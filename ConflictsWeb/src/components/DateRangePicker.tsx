import { Dayjs } from 'dayjs';
import {DatePicker} from "@mui/x-date-pickers";

export default function DateRangePicker({value, onChange}: {value: Dayjs[] | undefined; onChange: (newValue: Dayjs[]) => void}) {

    return (
        <><DatePicker
            label="Min"
            value={value ? value[0] : undefined}
            onChange={(newValue) => {
                if (newValue === null || !value) return;
                const newRange = value.slice();
                if(newValue.isBefore(value[0])) {
                    newRange[0] = newValue;
                }
                onChange(newRange);
            }}/><DatePicker
            label="Max"
            value={value ? value[1] : undefined}
            onChange={(newValue) => {
                if (newValue === null || !value) return;
                const newRange  = value.slice();
                if(newValue.isAfter(value[1])) {
                    newRange[1] = newValue;
                }
                onChange(newRange);
            }}/></>);
}