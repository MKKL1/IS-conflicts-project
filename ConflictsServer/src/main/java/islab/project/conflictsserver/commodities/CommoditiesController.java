package islab.project.conflictsserver.commodities;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/commodities")
@RestController
public class CommoditiesController {
    private final CommoditiesService commoditiesService;

    public CommoditiesController(CommoditiesService commoditiesService) {
        this.commoditiesService = commoditiesService;
    }

    @GetMapping()
    public List<CommodityPriceDTO> findValuesByCategory(@RequestParam(name = "type") String type,
                                                                        @RequestParam(name = "region") String region,
                                                                        @RequestParam(name = "unit") String unit) {
        return commoditiesService.findValuesByCategory(new CommodityCategory(type, region, unit));
    }
}
