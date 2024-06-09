import {DatePrice} from "../models/DatePrice.ts";

export function convertToCandlestickData(data: DatePrice[], groupByMonths: number): {x: Date, y: [number, number, number, number]}[] {
    if (groupByMonths <= 0) {
        throw new Error("groupByMonths must be greater than 0");
    }

    // Sort data by date to ensure proper grouping
    const sortedData = data.slice().sort((a, b) => a.date.getTime() - b.date.getTime());

    const result: {x: Date, y: [number, number, number, number]}[] = [];
    let currentGroup: DatePrice[] = [];

    sortedData.forEach((record, index) => {
        // Add the current record to the current group
        currentGroup.push(record);

        // Check if the current group has reached the desired number of months or it's the last record
        const groupIsComplete = currentGroup.length === groupByMonths;
        const isLastRecord = index === sortedData.length - 1;

        if (groupIsComplete || isLastRecord) {
            const open = currentGroup[0].price;
            const close = currentGroup[currentGroup.length - 1].price;
            const high = Math.max(...currentGroup.map(r => r.price));
            const low = Math.min(...currentGroup.map(r => r.price));
            const date = currentGroup[0].date;

            result.push({ x: date, y: [open, high, low, close] });
            currentGroup = [];
        }
    });

    return result;
}
