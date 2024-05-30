import dayjs, { Dayjs } from 'dayjs';
import {DatePicker} from "@mui/x-date-pickers";
import {useState} from "react";

export default function DateRangePicker({initialRange, onChange}: {initialRange: Dayjs[]; onChange: (newValue: Dayjs[]) => void}) {
    const [range, setRange] = useState<Dayjs[]>(initialRange);

    return (
        <><DatePicker
            label="Min"
            value={range[0]}
            onChange={(newValue) => {
                if (newValue === null) return;
                const newRange = range.slice();
                if(newValue.isAfter(range[0])) {
                    newRange[0] = newValue;
                }
                setRange(newRange);
                onChange(newRange);
            }}/><DatePicker
            label="Max"
            value={range[1]}
            onChange={(newValue) => {
                if (newValue === null) return;
                const newRange  = range.slice();
                if(newValue.isBefore(range[1])) {
                    newRange[1] = newValue;
                }
                setRange(newRange);
                onChange(newRange);
            }}/></>);
}