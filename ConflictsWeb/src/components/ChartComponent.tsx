import {
    ChartsAxisHighlight,
    ChartsLegend, ChartsReferenceLine, ChartsTooltip,
    ChartsXAxis,
    ChartsYAxis,
    LineHighlightPlot,
    LinePlot,
    ResponsiveChartContainer
} from "@mui/x-charts";
import {Paper} from "@mui/material";
import { Dayjs } from 'dayjs';
import Chart from 'react-apexcharts';
import {DatePrice} from "../models/DatePrice.ts";
import {useEffect, useState} from "react";
import {convertToCandlestickData} from "../utils/candlestickConverter.ts";


export default function ChartComponent({dataset, range, conflictRange}: {dataset: DatePrice[], range: Dayjs[] | undefined, conflictRange: Dayjs[] | undefined}) {
    const [candleStickData, setCandleStickData] = useState<{x: Date, y: [number, number, number, number]}[] | undefined>();

    useEffect(() => {
        if(dataset) {
            setCandleStickData(convertToCandlestickData(dataset));
        }
    }, [dataset]);

    const state = {
        options: {
            chart: {
                id: 'commodity-price-chart',
                toolbar: {
                    autoSelected: 'pan',
                    show: false
                },
                zoom: {
                    enabled: false
                },
            },
            xaxis: {
                type: 'datetime'
            }
        },
        series: [{
            data: candleStickData
        }]
    }


    return (
        candleStickData &&
        <Paper sx={{ width: '100%', height: 300 }} elevation={3}>
            {/*<ResponsiveChartContainer*/}
            {/*    dataset={dataset}*/}
            {/*    series={[*/}
            {/*        {*/}
            {/*            type: 'line',*/}
            {/*            dataKey: 'price',*/}
            {/*            valueFormatter: (value) => {*/}
            {/*                return `${value}$`*/}
            {/*            },*/}
            {/*        },*/}
            {/*    ]}*/}
            {/*    xAxis={[*/}
            {/*        {*/}
            {/*            dataKey: 'date',*/}
            {/*            scaleType: 'time',*/}
            {/*            id: 'x-axis-id',*/}
            {/*            min: range[0].toDate(),*/}
            {/*            max: range[1].toDate(),*/}

            {/*        },*/}
            {/*    ]}*/}
            {/*    {...chartSetting}*/}

            {/*>*/}
            {/*    <LinePlot />*/}
            {/*    <ChartsXAxis />*/}
            {/*    <ChartsYAxis />*/}
            {/*    {*/}
            {/*        conflictRange &&*/}
            {/*        <>*/}
            {/*            <ChartsReferenceLine*/}
            {/*                x={conflictRange[0].toDate()}*/}
            {/*                label="Start"*/}
            {/*                lineStyle={{ stroke: 'red' }}*/}
            {/*            />*/}
            {/*            <ChartsReferenceLine*/}
            {/*                x={conflictRange[1].toDate()}*/}
            {/*                label="End"*/}
            {/*                lineStyle={{ stroke: 'red' }}*/}
            {/*            />*/}
            {/*        </>*/}
            {/*    }*/}
            {/*    <ChartsLegend />*/}
            {/*    <LineHighlightPlot/>*/}
            {/*    <ChartsAxisHighlight x="line" />*/}
            {/*    <ChartsTooltip trigger="axis" />*/}
            {/*    <ChartsXAxis label="Data" position="bottom" axisId="x-axis-id" />*/}
            {/*</ResponsiveChartContainer>*/}

            <Chart options={state.options} series={state.series} type="candlestick" height={'100%'} width={'100%'}/>

        </Paper>
    );
}