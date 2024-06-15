import {number, string} from "yup";

export class Conflict {
    id: number;
    location: string;
    sideA: string;
    sideB: string;
    conflictType: string;
    conflictIntensity: string;
    startTime: string;
    endTime: string|undefined;


    constructor(id: number, location: string, sideA: string, sideB: string, conflictType: string, conflictIntensity: string, startTime: string, endTime: string | undefined) {
        this.id = id;
        this.location = location;
        this.sideA = sideA;
        this.sideB = sideB;
        this.conflictType = conflictType;
        this.conflictIntensity = conflictIntensity;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}