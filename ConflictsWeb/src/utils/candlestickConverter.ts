import {DatePrice} from "../models/DatePrice.ts";

export function convertToCandlestickData(data: DatePrice[]): {x: Date, y: [number, number, number, number]}[] {
    return data.map((record, index, array) => {
        const open = record.price;
        const close = array[index + 1] ? array[index + 1].price : record.price;
        const high = Math.max(open, close);
        const low = Math.min(open, close);

        return {x: record.date, y: [open, high, low, close]};
    });
}
