import {MenuItem, TextField} from "@mui/material";
import {Button, Stack} from "react-bootstrap";
import {useEffect, useState} from "react";
import ChartComponent from "./ChartComponent.tsx";
import axios from "axios";
import {OverviewResponse} from "../models/OverviewResponse.ts";
import {useNotificationContext} from "../contexts/NotificationContext.tsx";
import {NotificationVariants} from "../NotificationVariants.ts";
import dayjs, {Dayjs} from "dayjs";
import DateRangePicker from "./DateRangePicker.tsx";

export default function ChartPage() {
    const {pushNotification} = useNotificationContext();

    const [overviewData, setOverviewData] = useState<OverviewResponse>();
    const [commodityIndex, setCommodityIndex] = useState<number>(0);
    const [conflictIndex, setConflictIndex] = useState<number>(0);
    const [dataset, setDataset] = useState([]);
    const [range, setRange] = useState<Dayjs[]>();
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

    useEffect(() => {
        const conflict = getConflict(conflictIndex);
        if(conflict) {
            const enddate = conflict.end ? dayjs(conflict.end) : dayjs()
            setRange([dayjs(conflict.start).add(-2, 'month'), enddate.add(2, 'month')])
            setconflictRange([dayjs(conflict.start), dayjs(conflict.end)])
        }
    }, [conflictIndex]);

    function updateChart() {
        const commodity = getCommodity(commodityIndex);
        axios.get(`http://localhost:8080/api/commodities/${commodity?.id}`)
            .then(res => {
                console.log(res.data);
                const formattedResponse = res.data.map((item: {date: string; price: number;}) => ({
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

    return (
        <Stack>
            <Stack direction="horizontal" gap={3}>
                <TextField
                    select
                    sx={{ minWidth: 150 }}
                    label="commodity"
                    value={getCommodity(commodityIndex)}
                    onChange={(event) => {
                        console.log(event.target.value)
                        setCommodityIndex(Number(event.target.value))
                        updateChart()
                    }}
                >
                    {overviewData?.commodities.map((commodity, index) => (
                        <MenuItem key={index} value={index}>{commodity.type} - {commodity.region}</MenuItem>
                    ))}
                </TextField>
                <TextField
                    select
                    sx={{ minWidth: 150 }}
                    label="conflict"
                    value={getConflict(conflictIndex)}
                    onChange={(event) => {
                        console.log(event.target.value)
                        setConflictIndex(Number(event.target.value));
                    }}
                >
                    {overviewData?.conflicts.map((conflict, index) => (
                        <MenuItem key={index} value={index}>{conflict.location} - [{conflict.start} - {conflict.end}]</MenuItem>
                    ))}
                </TextField>
                {/*<Button onClick={updateChart}>*/}
                {/*    Update*/}
                {/*</Button>*/}
            </Stack>
            <ChartComponent dataset={dataset} range={range} conflictRange={conflictRange}/>
            <DateRangePicker
                value={range}
                onChange={(newValue) => {
                    console.log(newValue);
                    setRange(newValue);
                }}
            />
        </Stack>
    )
}