import {CommodityCategory} from "./CommodityCategory.ts";
import {Conflict} from "./Conflict.ts";

export class OverviewResponse {
    public commodities: CommodityCategory[];
    public conflicts: Conflict[];

    constructor(commodities: CommodityCategory[], conflicts: Conflict[]) {
        this.commodities = commodities;
        this.conflicts = conflicts;
    }
}