package islab.project.conflictsserver.commodities;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CommoditiesService {

    private final CommoditiesRepository commoditiesRepository;

    public CommoditiesService(CommoditiesRepository commoditiesRepository) {
        this.commoditiesRepository = commoditiesRepository;
    }

    //save
    public void saveResourcesList(List<Commodity> resourcesList) {
        commoditiesRepository.saveCommodityList(resourcesList);
    }

    //read
    public List<Commodity> findAll() {
        return StreamSupport.stream(commoditiesRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
