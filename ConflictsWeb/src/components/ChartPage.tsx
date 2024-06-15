import {MenuItem, TextField, Button, Box} from "@mui/material";
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

export default function ChartPage() {
    const {pushNotification} = useNotificationContext();

    const [overviewData, setOverviewData] = useState<OverviewResponse>();
    const [commodityIndex, setCommodityIndex] = useState<number>(-1);
    const [groupBy, setGroupBy] = useState<number>(12);
    const [conflictIndex, setConflictIndex] = useState<number>(-1);
    const [dataset, setDataset] = useState([]);
    // const [range, setRange] = useState<Dayjs[]>();
    const [conflictRange, setconflictRange] = useState<Dayjs[]>();

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

    // useEffect(() => {
    //     const conflict = getConflict(conflictIndex);
    //     if(conflict) {
    //         const enddate = conflict.end ? dayjs(conflict.end) : dayjs();
    //         setRange([dayjs(conflict.start).add(-2, 'month'), enddate.add(2, 'month')]);
    //         setconflictRange([dayjs(conflict.start), dayjs(conflict.end)]);
    //     }
    // }, [conflictIndex]);

    function updateChart() {
        const commodity = getCommodity(commodityIndex);
        if (commodity) {
            axios.get(`http://localhost:8080/api/commodities/${commodity?.id}`)
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

    function handleExport(format: 'json' | 'xml') {
        const conflict = getConflict(conflictIndex);
        const commodity = getCommodity(commodityIndex);
        if (conflictIndex === -1 || commodityIndex === -1 || !dataset.length) {
            pushNotification("Please select a conflict and a commodity, and make sure data is loaded", NotificationVariants.warning);
            return;
        }
        const data = { conflict, commodity, dataset };
        if (format === 'json') {
            exportDataAsJSON(data);
        } else {
            exportDataAsXML(data);
        }
    }

    return (
        <Box>
            <Box display="flex" gap={3} mb={2}>
                { overviewData?.commodities &&
                    <ConflictTable
                        rows={overviewData?.conflicts}>
                    </ConflictTable>
                }
            </Box>

            <ChartComponent dataset={dataset} groupByMonths={groupBy} conflictRange={conflictRange}/>
            <Box display="flex" gap={3} mb={2}>
                <TextField
                    select
                    sx={{ minWidth: 150 }}
                    label="commodity"
                    value={commodityIndex}
                    onChange={(event) => {
                        setCommodityIndex(Number(event.target.value));
                        updateChart();
                    }}
                >
                    {overviewData?.commodities.map((commodity, index) => (
                        <MenuItem key={index} value={index}>{commodity.type} - {commodity.region}</MenuItem>
                    ))}
                </TextField>
                <TextField
                    select
                    sx={{ minWidth: 150 }}
                    label="group by"
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
            {/*<DateRangePicker*/}
            {/*    value={range}*/}
            {/*    onChange={(newValue) => {*/}
            {/*        setRange(newValue);*/}
            {/*    }}*/}
            {/*/>*/}
            <Box display="flex" gap={3} mt={2}>
                <Button variant="contained" color="primary" onClick={() => handleExport('json')}>
                    Export as JSON
                </Button>
                <Button variant="contained" color="secondary" onClick={() => handleExport('xml')}>
                    Export as XML
                </Button>
            </Box>
        </Box>
    );
}
