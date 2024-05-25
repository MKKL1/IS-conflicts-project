package islab.project.conflictsserver.commodities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record CommodityPriceDTO(Double price, @JsonFormat(pattern="yyyy-MM-dd") LocalDate date) {
}
