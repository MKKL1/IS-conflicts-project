import React, { useState } from "react";
import { Box, Button, MenuItem, Select, Typography, TextField } from "@mui/material";
import axios from "axios";
import { useNotificationContext } from "../contexts/NotificationContext";
import { NotificationVariants } from "../NotificationVariants";
import { validateFile } from "../utils/fileValidation";
import { useAuthContext } from "../contexts/AuthContext";
import "../ImportPage.css";

// Statyczna lista plików
const files = [
    "CMO-Historical-Data-Monthly.xlsx",
    "MainConflictTable.xls",
    "MetalsPricesData.csv",
    "CoalPricesData.csv",
    "CrudeOilPricesData.csv",
    "GasPricesData.csv",
    "GoldPricesData.csv",
    "AdditionalConflictTable.xls",
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
    "AdditionalConflictTable.xls": "conflicts",
};

export default function ImportPage() {
    const { pushNotification } = useNotificationContext();
    const { token } = useAuthContext(); // Retrieve the token from AuthContext
    const [selectedFileType, setSelectedFileType] = useState<string>("");
    const [customFile, setCustomFile] = useState<File | null>(null);
    const [message, setMessage] = useState<string>("");

    const handleFileChange = (event: React.ChangeEvent<{ value: unknown }>) => {
        setSelectedFileType(event.target.value as string);
    };

    const handleCustomFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files[0]) {
            setCustomFile(event.target.files[0]);
        }
    };

    const handleImport = () => {
        setMessage(""); // Reset message before new import

        if (customFile) {
            const validationError = validateFile(customFile);
            if (validationError) {
                setMessage(validationError);
                pushNotification(validationError, NotificationVariants.danger);
                return;
            }

            sendFileToApi(customFile, fileApiNameMap[selectedFileType]);

        } else {
            pushNotification("Select a file before uploading", NotificationVariants.danger);
        }
    };

    const sendFileToApi = (file: File, fileType: string) => {
        const formData = new FormData();
        formData.append("file", file, file.name);

        axios.post(`http://localhost:8080/api/imports/${fileType}`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
                "Authorization": `Bearer ${token}` // Add the token to the headers
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
                value={selectedFileType}
                onChange={handleFileChange}
                displayEmpty
                sx={{ minWidth: 200, mb: 2 }}
            >
                <MenuItem value="" disabled>Select a file</MenuItem>
                {files.map((file, index) => (
                    <MenuItem key={index} value={file}>{file}</MenuItem>
                ))}
            </Select>
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
                disabled={!selectedFileType && !customFile}
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
