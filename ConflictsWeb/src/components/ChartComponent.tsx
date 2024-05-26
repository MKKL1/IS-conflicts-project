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
import {useEffect, useState} from "react";

const chartSetting = {
    yAxis: [
        {
            label: 'price',
        },
    ]
};
const minDistance = 10;

export default function ChartComponent({dataset}: {dataset: any[]}) {
    // const [xLimits, setXLimites] = useState<Date[]>([new Date(1999, 1, 1), new Date()]);
    // const [domain, setDomain] = useState<Date[]>([new Date(1999, 1, 1), new Date()]);
    //
    // useEffect(() => {
    //
    // }, [dataset]);
    //
    // const handleChange = (
    //     event: Event,
    //     newValue: Date | Date[],
    //     activeThumb: Date,
    // ) => {
    //     if (!Array.isArray(newValue)) {
    //         return;
    //     }
    //
    //     if (newValue[1] - newValue[0] < minDistance) {
    //         if (activeThumb === 0) {
    //             const clamped = Math.min(newValue[0], 100 - minDistance);
    //             setXLimites([clamped, clamped + minDistance]);
    //         } else {
    //             const clamped = Math.max(newValue[1], minDistance);
    //             setXLimites([clamped - minDistance, clamped]);
    //         }
    //     } else {
    //         setXLimites(newValue as number[]);
    //     }
    // };

    return (
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
                        // min: xLimits[0],
                        // max: xLimits[1],

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

            {/*<Slider*/}
            {/*    value={xLimits}*/}
            {/*    onChange={handleChange}*/}
            {/*    valueLabelDisplay="auto"*/}
            {/*    min={domain[0]}*/}
            {/*    max={domain[1]}*/}
            {/*    sx={{ mt: 2 }}*/}
            {/*/>*/}
        </Paper>
    );
}