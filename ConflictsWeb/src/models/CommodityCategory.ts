export class CommodityCategory {
    public id: number;
    public type: string;
    public region: string;
    public unit: string;


    constructor(id: number, type: string, region: string, unit: string) {
        this.id = id;
        this.type = type;
        this.region = region;
        this.unit = unit;
    }
}