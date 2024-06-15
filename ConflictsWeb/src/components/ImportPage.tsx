import { useState } from "react";
import { Box, Button, MenuItem, Select, Typography, TextField } from "@mui/material";
import axios from "axios";
import { useNotificationContext } from "../contexts/NotificationContext";
import { NotificationVariants } from "../NotificationVariants";
import { validateFile } from "../utils/fileValidation"; // Importowanie funkcji walidacji
import "../ImportPage.css"; // Importowanie pliku CSS

// Statyczna lista plików
const files = [
    "CMO-Historical-Data-Monthly.xlsx",
    "MainConflictTable.xls",
    "MetalsPricesData.csv",
    "CoalPricesData.csv",
    "CrudeOilPricesData.csv",
    "GasPricesData.csv",
    "GoldPricesData.csv",
];

// Mapowanie plików do odpowiednich wartości 'name' dla endpointu API
const fileApiNameMap: { [key: string]: string } = {
    "CMO-Historical-Data-Monthly.xlsx": "cmo-historical",
    "MainConflictTable.xls": "conflicts",
    "MetalsPricesData.csv": "metals",
    "CoalPricesData.csv": "coal",
    "CrudeOilPricesData.csv": "crudeOil",
    "GasPricesData.csv": "gas",
    "GoldPricesData.csv": "gold",
};

export default function ImportPage() {
    const { pushNotification } = useNotificationContext();
    const [selectedFile, setSelectedFile] = useState<string | File>("");
    const [customFile, setCustomFile] = useState<File | null>(null);
    const [message, setMessage] = useState<string>("");

    const handleFileChange = (event: React.ChangeEvent<{ value: unknown }>) => {
        setSelectedFile(event.target.value as string);
        setCustomFile(null); // Reset custom file if a predefined file is selected
    };

    const handleCustomFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files[0]) {
            setCustomFile(event.target.files[0]);
            setSelectedFile(""); // Reset selected file if a custom file is chosen
        }
    };

    const handleImport = () => {
        setMessage(""); // Reset message before new import
        let fileToUpload;
        let apiName;

        if (customFile) {
            const validationError = validateFile(customFile);
            if (validationError) {
                setMessage(validationError);
                pushNotification(validationError, NotificationVariants.danger);
                return;
            }
            fileToUpload = customFile;
            apiName = "custom";
        } else if (selectedFile) {
            apiName = fileApiNameMap[selectedFile];
            if (!apiName) {
                const errorMessage = "No API name mapped for selected file";
                setMessage(errorMessage);
                pushNotification(errorMessage, NotificationVariants.danger);
                return;
            }
            // Fetch the file from the assets folder
            fetch(`src/assets/resources/${selectedFile}`)
                .then(response => response.blob())
                .then(blob => {
                    fileToUpload = new File([blob], selectedFile);
                    sendFileToApi(fileToUpload, apiName);
                })
                .catch(error => {
                    const errorMessage = "Failed to load the file";
                    setMessage(errorMessage);
                    pushNotification(errorMessage, NotificationVariants.danger);
                    console.error(error);
                });
            return; // Exit early because fetch is asynchronous
        } else {
            const warningMessage = "Please select or upload a file";
            setMessage(warningMessage);
            pushNotification(warningMessage, NotificationVariants.warning);
            return;
        }

        sendFileToApi(fileToUpload, apiName);
    };

    const sendFileToApi = (file: File, apiName: string) => {
        const formData = new FormData();
        formData.append("file", file, file.name);

        axios.post(`http://localhost:8080/api/imports/${apiName}`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        })
            .then(() => {
                const successMessage = "Data imported successfully";
                setMessage(successMessage);
                pushNotification(successMessage, NotificationVariants.success);
            })
            .catch(error => {
                const errorMessage = "Failed to import data";
                setMessage(errorMessage);
                pushNotification(errorMessage, NotificationVariants.danger);
                console.error(error);
            });
    };

    return (
        <Box className="import-container">
            <Typography variant="h4" mb={2}>Import Data</Typography>
            <Typography variant="h6" mb={2}>Select file to import</Typography>
            <Select
                value={typeof selectedFile === "string" ? selectedFile : ""}
                onChange={handleFileChange}
                displayEmpty
                sx={{ minWidth: 200, mb: 2 }}
            >
                <MenuItem value="" disabled>Select a file</MenuItem>
                {files.map((file, index) => (
                    <MenuItem key={index} value={file}>{file}</MenuItem>
                ))}
            </Select>
            <Typography variant="h6" mb={2}>Or upload a custom file</Typography>
            <TextField
                type="file"
                onChange={handleCustomFileChange}
                sx={{ mb: 2 }}
                inputProps={{ accept: ".xlsx,.xls,.csv" }} // akceptowane typy plików
            />
            <Button
                variant="contained"
                color="primary"
                onClick={handleImport}
                disabled={!selectedFile && !customFile}
            >
                Import
            </Button>
            {message && (
                <Typography variant="body1" color="error" mt={2}>
                    {message}
                </Typography>
            )}
        </Box>
    );
}
