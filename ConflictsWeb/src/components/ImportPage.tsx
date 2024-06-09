import { useState } from "react";
import { Box, Button, MenuItem, Select, Typography } from "@mui/material";
import axios from "axios";
import { useNotificationContext } from "../contexts/NotificationContext.tsx";
import { NotificationVariants } from "../NotificationVariants.ts";

//Statyczna lista plików
const files = [
    "CMO-Historical-Data-Monthly.xlsx",
    "MainConflictTable.xls",
];

//Mapowanie plików do endpointów API
const fileApiMap: { [key: string]: string } = {
    "CMO-Historical-Data-Monthly.xlsx": "commodities/cmo-historical",
    "MainConflictTable.xls": "conflicts",
};

export default function ImportPage() {
    const { pushNotification } = useNotificationContext();
    const [selectedFile, setSelectedFile] = useState<string>("");

    const handleFileChange = (event: React.ChangeEvent<{ value: unknown }>) => {
        setSelectedFile(event.target.value as string);
    };

    const handleImport = () => {
        if (!selectedFile) {
            pushNotification("Please select a file", NotificationVariants.warning);
            return;
        }

        const endpoint = fileApiMap[selectedFile];
        if (!endpoint) {
            pushNotification("No API endpoint mapped for selected file", NotificationVariants.danger);
            return;
        }

        // Fetch the file from the assets folder
        fetch(`src/assets/resources/${selectedFile}`)
            .then(response => response.blob())
            .then(blob => {
                const formData = new FormData();
                formData.append("file", blob, selectedFile);

                axios.post(`http://localhost:8080/api/imports/${endpoint}`, formData, {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                })
                    .then((response) => {
                        pushNotification("Data imported successfully", NotificationVariants.success);
                    })
                    .catch((error) => {
                        pushNotification("Failed to import data", NotificationVariants.danger);
                        console.error(error);
                    });
            })
            .catch(error => {
                pushNotification("Failed to load the file", NotificationVariants.danger);
                console.error(error);
            });
    };

    return (
        <Box>
            <Typography variant="h4" mb={2}>Import Data</Typography>
            <Select
                value={selectedFile}
                onChange={handleFileChange}
                displayEmpty
                sx={{ minWidth: 200, mb: 2 }}
            >
                <MenuItem value="" disabled>Select a file</MenuItem>
                {files.map((file, index) => (
                    <MenuItem key={index} value={file}>{file}</MenuItem>
                ))}
            </Select>
            <Button
                variant="contained"
                color="primary"
                onClick={handleImport}
                disabled={!selectedFile}
            >
                Import
            </Button>
        </Box>
    );
}