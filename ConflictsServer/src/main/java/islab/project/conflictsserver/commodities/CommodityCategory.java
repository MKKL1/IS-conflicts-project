package islab.project.conflictsserver.commodities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "commodity_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommodityCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private String region;
    private String unit;

    @OneToMany(mappedBy = "commodityCategory")
    private Set<CommodityPrice> prices;
}
