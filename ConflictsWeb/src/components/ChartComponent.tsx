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

const chartSetting = {
    yAxis: [
        {
            label: 'price',
        },
    ]
};

export default function ChartComponent({dataset, range, conflictRange}: {dataset: any[], range: Dayjs[] | undefined, conflictRange: Dayjs[] | undefined}) {
    // const [initialRange, setInitialRange] = useState<Dayjs[]>();

    // useEffect(() => {
    //     const dates = dataset.map(item => item.date);
    //     const smallestDate = dayjs(new Date(Math.min(...dates)));
    //     const largestDate = dayjs(new Date(Math.max(...dates)));
    //     setInitialRange([smallestDate, largestDate]);
    //     setRange(initialRange);
    // }, [dataset]);

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
                {
                    conflictRange &&
                    <>
                        <ChartsReferenceLine
                            x={conflictRange[0].toDate()}
                            label="Start"
                            lineStyle={{ stroke: 'red' }}
                        />
                        <ChartsReferenceLine
                            x={conflictRange[1].toDate()}
                            label="End"
                            lineStyle={{ stroke: 'red' }}
                        />
                    </>
                }
                <ChartsLegend />
                <LineHighlightPlot/>
                <ChartsAxisHighlight x="line" />
                <ChartsTooltip trigger="axis" />
                <ChartsXAxis label="Data" position="bottom" axisId="x-axis-id" />
            </ResponsiveChartContainer>

        </Paper>
    );
}