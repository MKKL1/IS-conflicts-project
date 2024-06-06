export class Conflict {
    id: number;
    location: string;
    start: string;
    end: string|undefined;

    constructor(id: number, location: string, start: string, end: string|undefined) {
        this.id = id;
        this.location = location;
        this.start = start;
        this.end = end;
    }
}