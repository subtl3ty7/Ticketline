package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.repository.MerchandiseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.MerchandiseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class CustomMerchandiseService implements MerchandiseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final MerchandiseRepository merchandiseRepository;

    @Autowired
    public CustomMerchandiseService(MerchandiseRepository merchandiseRepository) {
        this.merchandiseRepository = merchandiseRepository;
    }


    @Override
    public List<Merchandise> findAllMerchandiseProducts() {
        List<Merchandise> allMerchandiseProducts = merchandiseRepository.findAll();
        return allMerchandiseProducts;
    }
}
