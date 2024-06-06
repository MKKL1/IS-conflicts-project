package islab.project.conflictsserver.commodities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "commodities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommodityPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private CommodityCategory commodityCategory;

    private Double price;
    private LocalDate date;

}
