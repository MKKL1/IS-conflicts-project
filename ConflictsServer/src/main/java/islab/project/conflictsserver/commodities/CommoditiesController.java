package islab.project.conflictsserver.commodities;

import islab.project.conflictsserver.conflict.ConflictDTO;
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

    @GetMapping("/{id}")
    public List<CommodityPriceDTO> findValuesByCategory(@PathVariable() Integer id) {
        return commoditiesService.findValuesByCategory(id);
    }
}
