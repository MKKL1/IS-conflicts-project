import {Paper} from "@mui/material";
import { Dayjs } from 'dayjs';
import Chart from 'react-apexcharts';
import {DatePrice} from "../models/DatePrice.ts";
import {useEffect, useState} from "react";
import {convertToCandlestickData} from "../utils/candlestickConverter.ts";


export default function ChartComponent({dataset, groupByMonths, conflictRange}: {dataset: DatePrice[], groupByMonths: number, conflictRange: Dayjs[] | undefined}) {
    const [candleStickData, setCandleStickData] = useState<{x: Date, y: [number, number, number, number]}[] | undefined>();
    const [linearData, setLinearData] = useState<{ x: Date, y: number }[] | undefined>();
    const [range, setRange] = useState<Date[] | undefined>();

    useEffect(() => {
        if(dataset) {
            const a = convertToCandlestickData(dataset, groupByMonths);
            setCandleStickData(a);

            const volumeData = dataset.map(record => ({
                x: record.date,
                y: record.price
            }));
            setLinearData(volumeData);

            const dates = a.map(d => d.x);
            const minDate = new Date(Math.min(...dates.map(date => date.getTime())));
            const maxDate = new Date(Math.max(...dates.map(date => date.getTime())));
            setRange([minDate, maxDate]);
        }
    }, [dataset, groupByMonths]);

    const state = {
        options: {
            chart: {
                id: 'commodity-price-chart',
            },
            annotations: {
                xaxis: conflictRange ? [
                    {
                        x: conflictRange[0].unix() * 1000,
                        x2: conflictRange[1].unix() * 1000,
                        fillColor: '#B3F7CA',
                        opacity: 0.4,
                        label: {
                            borderColor: '#B3F7CA',
                            style: {
                                fontSize: '10px',
                                color: '#fff',
                                background: '#00E396',
                            },
                            offsetY: -10,
                            text: 'Conflict Range',
                        }
                    }
                ] : []
            },
            xaxis: {
                type: 'datetime'
            }
        },
        series: [{
            data: candleStickData
        }],
        seriesBar: [{
            data: linearData
        }],
        optionsBar: {
            chart: {
                height: 160,
                type: 'area',
                brush: {
                    enabled: true,
                    target: 'commodity-price-chart'
                },
                selection: {
                    enabled: true,
                    xaxis: {
                        min: range ? range[0].getTime() : undefined,
                        max: range ? range[1].getTime() : undefined
                    },
                    fill: {
                        color: '#ccc',
                        opacity: 0.4
                    },
                    stroke: {
                        color: '#0D47A1',
                    }
                },
            },
            dataLabels: {
                enabled: false
            },
            plotOptions: {
                bar: {
                    columnWidth: '80%',
                    colors: {
                        ranges: [{
                            from: -1000,
                            to: 0,
                            color: '#F15B46'
                        }, {
                            from: 1,
                            to: 10000,
                            color: '#FEB019'
                        }],

                    },
                }
            },
            stroke: {
                curve: 'straight'
            },
            xaxis: {
                type: 'datetime',
                // axisBorder: {
                //     offsetX: 13
                // }
            },
            // yaxis: {
            //     labels: {
            //         show: false
            //     }
            // }
        },
    }


    return (
        candleStickData &&
        <Paper sx={{ width: '100%', height: 480 }} elevation={3}>

            <Chart options={state.options} series={state.series} type="candlestick" height={300} width={'100%'}/>

            <Chart options={state.optionsBar} series={state.seriesBar} type="area" height={130} width={'100%'}/>

        </Paper>
    );
}