import {DatePrice} from "../models/DatePrice.ts";

export interface CandlestickData {
    x: Date;
    y: [number, number, number, number];
}

export function convertToCandlestickData(data: DatePrice[], groupByMonths: number): CandlestickData[] {
    if (groupByMonths <= 0) {
        throw new Error("groupByMonths must be greater than 0");
    }

    const sortedData = data.slice().sort((a, b) => a.date.getTime() - b.date.getTime());

    const result: CandlestickData[] = [];
    let currentGroup: DatePrice[] = [];
    let startDate: Date | null = null;

    sortedData.forEach((record, index) => {
        if (startDate === null) {
            startDate = record.date;
        }

        const monthsDifference = (record.date.getFullYear() - startDate.getFullYear()) * 12 + (record.date.getMonth() - startDate.getMonth());

        if (monthsDifference >= groupByMonths) {
            if (currentGroup.length > 0) {
                const open = currentGroup[0].price;
                const close = currentGroup[currentGroup.length - 1].price;
                const high = Math.max(...currentGroup.map(r => r.price));
                const low = Math.min(...currentGroup.map(r => r.price));
                const date = currentGroup[0].date;

                result.push({ x: date, y: [open, high, low, close] });
                currentGroup = [];
            }
            startDate = record.date;
        }

        currentGroup.push(record);

        const isLastRecord = index === sortedData.length - 1;
        if (isLastRecord && currentGroup.length > 0) {
            const open = currentGroup[0].price;
            const close = currentGroup[currentGroup.length - 1].price;
            const high = Math.max(...currentGroup.map(r => r.price));
            const low = Math.min(...currentGroup.map(r => r.price));
            const date = currentGroup[0].date;

            result.push({ x: date, y: [open, high, low, close] });
        }
    });

    // If grouping isn't successful, fallback to forcefully convert linear data to candlestick data
    if (result.length <= 1) {
        return sortedData.map(record => ({
            x: record.date,
            y: [record.price, record.price, record.price, record.price]
        }));
    }

    return result;
}
