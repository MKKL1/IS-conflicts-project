export class Conflict {
    id: number;
    location: string;
    start: string;
    end: string;

    constructor(id: number, location: string, start: string, end: string) {
        this.id = id;
        this.location = location;
        this.start = start;
        this.end = end;
    }
}