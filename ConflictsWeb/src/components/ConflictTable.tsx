// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {DataGrid, GridCallbackDetails, GridColDef, GridRowId, GridRowSelectionModel} from '@mui/x-data-grid';
import {Conflict} from '../models/Conflict';

interface ConflictTableProps {
    rows: Conflict[];
    onSelectionModelChange: (rowSelectionModel: GridRowSelectionModel, details: GridCallbackDetails) => void;
}

export default function ConflictTable({rows, onSelectionModelChange}: ConflictTableProps) {
    const columns: GridColDef[] = [
        // {field: 'id', headerName: 'ID', width: 70},
        // {field: 'location', headerName: 'Location', width: 130},
        {field: 'sideA', headerName: 'Side A', width: 200},
        {field: 'sideB', headerName: 'Side B', width: 200},
        {
            field: 'conflictType',
            headerName: 'Conflict Type',
            width: 130,
            type: 'singleSelect',
            valueOptions:[
                {value: 'EXTRASYSTEMIC', label: 'Extra systemic'},
                {value: 'INTERSTATE', label: 'Interstate'},
                {value: 'INTERNAL', label: 'Internal'},
                {value: 'INTERNATIONALIZED', label: 'Internationalized'}
            ]
        },
        {
            field: 'conflictIntensity',
            headerName: 'Conflict Intensity',
            width: 130,
            type: 'singleSelect',
            valueOptions: [
                {value: 'MINOR', label: 'Minor'},
                {value: 'WAR', label: 'War'}
            ]
        },
        {
            field: 'startTime',
            type: 'date',
            headerName: 'Start Time',
            width: 130,
            valueGetter: (value) => value && new Date(value),
        },
        {
            field: 'endTime',
            type: 'date',
            headerName: 'End Time',
            width: 130,
            valueGetter: (value) => value && new Date(value),
        },
    ];

    return (
        <div style={{height: 500, width: '100%'}}>
            <DataGrid
                rows={rows}
                columns={columns}
                pageSizeOptions={[5, 10, 25]}
                checkboxSelection={true}
                disableMultipleRowSelection={true}
                autoPageSize
                // hideFooterSelectedRowCount
                onRowSelectionModelChange={onSelectionModelChange}
            />
        </div>
    );
}