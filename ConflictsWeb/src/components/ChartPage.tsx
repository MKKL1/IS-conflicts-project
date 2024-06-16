import {MenuItem, TextField, Button, Box, Container, Typography} from "@mui/material";
import {useEffect, useState} from "react";
import ChartComponent from "./ChartComponent.tsx";
import axios from "axios";
import {OverviewResponse} from "../models/OverviewResponse.ts";
import {useNotificationContext} from "../contexts/NotificationContext.tsx";
import {NotificationVariants} from "../NotificationVariants.ts";
import dayjs, {Dayjs} from "dayjs";
import DateRangePicker from "./DateRangePicker.tsx";
import { exportDataAsJSON, exportDataAsXML } from "../utils/dataExport.ts";
import ConflictTable from "./ConflictTable.tsx";
import {DatePrice} from "../models/DatePrice.ts";
import {GridCallbackDetails, GridRowSelectionModel} from "@mui/x-data-grid";

export default function ChartPage() {
    const {pushNotification} = useNotificationContext();

    const [overviewData, setOverviewData] = useState<OverviewResponse>();
    const [commodityIndex, setCommodityIndex] = useState<number>(-1);
    const [groupBy, setGroupBy] = useState<number>(12);
    const [conflictIndex, setConflictIndex] = useState<number>(-1);
    const [dataset, setDataset] = useState<DatePrice[]>([]);
    const [conflictRange, setConflictRange] = useState<Dayjs[]>();
    const [filteredConflicts, setFilteredConflicts] = useState([]);

    function getCommodity(index: number) {
        return overviewData?.commodities[index];
    }

    function getConflict(index: number) {
        return overviewData?.conflicts[index];
    }

    useEffect(() => {
        axios.get('http://localhost:8080/api/overview')
            .then(res => {
                console.log(res.data);
                setOverviewData(res.data);
            })
            .catch(err => {
                console.error(err);
                pushNotification("Error occurred during loading", NotificationVariants.danger);
            });
    }, [pushNotification]);

    useEffect(() => {
        if (commodityIndex >= 0) {
            const commodity = getCommodity(commodityIndex);
            if (commodity) {
                axios.get(`http://localhost:8080/api/commodities/${commodity.id}`)
                    .then(res => {
                        console.log(res.data);
                        const formattedResponse = res.data.map((item: DatePrice) => ({
                            ...item,
                            date: new Date(item.date)
                        }));
                        setDataset(formattedResponse);
                    })
                    .catch(err => {
                        console.error(err);
                        pushNotification("Error occurred during loading", NotificationVariants.danger);
                    });
            }
        }
    }, [commodityIndex, pushNotification]);

    useEffect(() => {
        if (dataset.length && overviewData?.conflicts) {
            const dates = dataset.map(item => item.date.getTime());
            const minDate = dayjs(Math.min(...dates));
            const maxDate = dayjs(Math.max(...dates));

            const filtered = overviewData.conflicts.filter(conflict => {
                const conflictStart = dayjs(conflict.startTime);
                const conflictEnd = dayjs(conflict.endTime);
                return (conflictStart.isBefore(maxDate) && conflictEnd.isAfter(minDate));
            });

            setFilteredConflicts(filtered);
        }
    }, [dataset, overviewData]);

    function handleExport(format: 'json' | 'xml') {
        const conflict = getConflict(conflictIndex);
        const commodity = getCommodity(commodityIndex);

        if (conflictIndex === -1) {
            pushNotification("Please select a conflict", NotificationVariants.warning);
            return;
        }

        if (commodityIndex === -1) {
            pushNotification("Please select a commodity", NotificationVariants.warning);
            return;
        }

        if (!dataset.length) {
            pushNotification("Please make sure data is loaded", NotificationVariants.warning);
            return;
        }

        const data = { conflict, commodity, dataset };
        if (format === 'json') {
            exportDataAsJSON(data);
        } else {
            exportDataAsXML(data);
        }
    }

    function handleSelectionModelChange(rowSelectionModel: GridRowSelectionModel, details: GridCallbackDetails) {
        if (rowSelectionModel.length > 0) {
            const selectedConflict = overviewData?.conflicts.find(conflict => conflict.id === rowSelectionModel[0]);
            if (selectedConflict) {
                setConflictRange([dayjs(selectedConflict.startTime), dayjs(selectedConflict.endTime)]);
                setConflictIndex(rowSelectionModel[0] as number);
            }
        } else {
            setConflictRange(undefined);
            setConflictIndex(-1);
        }
    }

    return (
        <Container maxWidth="xl">
            <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center" p={2}>
                <Box display="flex" gap={3} mb={2}>
                    <TextField
                        select
                        sx={{ minWidth: 150 }}
                        label="Commodity"
                        value={commodityIndex}
                        onChange={(event) => {
                            setCommodityIndex(Number(event.target.value));
                        }}
                    >
                        {overviewData?.commodities.map((commodity, index) => (
                            <MenuItem key={index} value={index}>{commodity.type} - {commodity.region}</MenuItem>
                        ))}
                    </TextField>
                    <TextField
                        select
                        sx={{ minWidth: 150 }}
                        label="Group by months"
                        value={groupBy}
                        onChange={(event) => {
                            setGroupBy(Number(event.target.value));
                        }}
                    >
                        <MenuItem key={1} value={1}>1</MenuItem>
                        <MenuItem key={3} value={3}>3</MenuItem>
                        <MenuItem key={6} value={6}>6</MenuItem>
                        <MenuItem key={12} value={12}>12</MenuItem>
                    </TextField>
                </Box>

                {commodityIndex === -1 && (
                    <Typography variant="h6" color="textSecondary">
                        Please select a commodity to view the data.
                    </Typography>
                )}

                {commodityIndex !== -1 && (
                    <>
                        <Box display="flex" gap={3} mb={2} width="100%">
                            {overviewData?.commodities && (
                                <ConflictTable
                                    rows={filteredConflicts}
                                    onSelectionModelChange={handleSelectionModelChange}
                                />
                            )}
                        </Box>

                        <ChartComponent dataset={dataset} groupByMonths={groupBy} conflictRange={conflictRange} />

                        <Box display="flex" gap={3} mt={2}>
                            <Button variant="contained" color="primary" onClick={() => handleExport('json')}>
                                Export as JSON
                            </Button>
                            <Button variant="contained" color="secondary" onClick={() => handleExport('xml')}>
                                Export as XML
                            </Button>
                        </Box>
                    </>
                )}
            </Box>
        </Container>
    );
}
