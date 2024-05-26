export class CommodityCategory {
    public type: string;
    public region: string;
    public unit: string;
    
    constructor(type: string, region: string, unit: string) {
        this.type = type;
        this.region = region;
        this.unit = unit;
    }
}