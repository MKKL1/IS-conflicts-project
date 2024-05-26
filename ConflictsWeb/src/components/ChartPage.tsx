import {MenuItem, TextField} from "@mui/material";
import {Button, Stack} from "react-bootstrap";
import {useEffect, useState} from "react";
import {CommodityCategory} from "../models/CommodityCategory.ts";
import ChartComponent from "./ChartComponent.tsx";
import axios from "axios";
import {OverviewResponse} from "../models/OverviewResponse.ts";
import {useNotificationContext} from "../contexts/NotificationContext.tsx";
import {NotificationVariants} from "../NotificationVariants.ts";
import {Conflict} from "../models/Conflict.ts";

export default function ChartPage() {
    const {pushNotification} = useNotificationContext();

    const [overviewData, setOverviewData] = useState<OverviewResponse>();
    const [commodity, setCommodity] = useState<CommodityCategory | undefined>(undefined);
    const [conflict, setConflict] = useState<Conflict | undefined>(undefined);
    const [dataset, setDataset] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/overview')
            .then(res => {
                console.log(res.data);
                setOverviewData(res.data);

                setCommodity(overviewData?.commodities[0])
                setConflict(overviewData?.conflicts[0])
            })
            .catch(err => {
                console.error(err);
                pushNotification("Error occurred during loading", NotificationVariants.danger);
            });
    }, [pushNotification]);

    function updateChart() {
        axios.get(`http://localhost:8080/api/commodities?type=${commodity?.type}&region=${commodity?.region}&unit=${commodity?.unit}`)
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
                    label="x-axis colorMap"
                    value={0}
                    onChange={(event) => {
                        console.log(event.target.value)
                        setCommodity(overviewData?.commodities[Number(event.target.value)])
                        console.log(commodity)
                    }}
                >
                    {overviewData?.commodities.map((commodity, index) => (
                        <MenuItem key={index} value={index}>{commodity.type} - {commodity.region}</MenuItem>
                    ))}
                </TextField>
                <TextField
                    select
                    sx={{ minWidth: 150 }}
                    label="x-axis colorMap"
                    value={0}
                    onChange={(event) => {
                        console.log(event.target.value)
                        setConflict(overviewData?.conflicts[Number(event.target.value)])
                        console.log(conflict)
                    }}
                >
                    {overviewData?.conflicts.map((conflict, index) => (
                        <MenuItem key={index} value={index}>{conflict.location} - {conflict.start}</MenuItem>
                    ))}
                </TextField>
                <Button onClick={updateChart}>
                    Update
                </Button>
            </Stack>
            <ChartComponent dataset={dataset}/>
        </Stack>
    )
}