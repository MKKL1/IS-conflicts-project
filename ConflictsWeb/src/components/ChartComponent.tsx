import {
    ChartsAxisHighlight,
    ChartsLegend, ChartsTooltip,
    ChartsXAxis,
    ChartsYAxis,
    LineHighlightPlot,
    LinePlot,
    ResponsiveChartContainer
} from "@mui/x-charts";
import {Paper, Slider} from "@mui/material";
import {useEffect, useRef, useState} from "react";
import dayjs, { Dayjs } from 'dayjs';
import DateRangePicker from "./DateRangePicker.tsx";

const chartSetting = {
    yAxis: [
        {
            label: 'price',
        },
    ]
};

export default function ChartComponent({dataset}: {dataset: any[]}) {
    const [range, setRange] = useState<Dayjs[]>();
    const [initialRange, setInitialRange] = useState<Dayjs[]>();

    useEffect(() => {
        const dates = dataset.map(item => item.date);
        const smallestDate = dayjs(new Date(Math.min(...dates)));
        const largestDate = dayjs(new Date(Math.max(...dates)));
        setInitialRange([smallestDate, largestDate]);
        setRange(initialRange);
    }, [dataset]);

    return (
        range &&
        <Paper sx={{ width: '100%', height: 300 }} elevation={3}>
            <ResponsiveChartContainer
                dataset={dataset}
                series={[
                    {
                        type: 'line',
                        dataKey: 'price',
                        valueFormatter: (value) => {
                            return `${value}$`
                        },
                    },
                ]}
                xAxis={[
                    {
                        dataKey: 'date',
                        scaleType: 'time',
                        id: 'x-axis-id',
                        min: range[0].toDate(),
                        max: range[1].toDate(),

                    },
                ]}
                {...chartSetting}

            >
                <LinePlot />
                <ChartsXAxis />
                <ChartsYAxis />
                {/*<MarkPlot />*/}
                <ChartsLegend />
                <LineHighlightPlot/>
                <ChartsAxisHighlight x="line" />
                <ChartsTooltip trigger="axis" />
                <ChartsXAxis label="Data" position="bottom" axisId="x-axis-id" />
            </ResponsiveChartContainer>

            {
                initialRange &&
                <DateRangePicker
                    initialRange={initialRange}
                    onChange={(newValue) => {
                        setRange(newValue);
                    }}
                />
            }

        </Paper>
    );
}