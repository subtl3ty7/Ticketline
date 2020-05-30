package at.ac.tuwien.sepm.groupphase.backend.datagenerator;


import at.ac.tuwien.sepm.groupphase.backend.repository.MerchandiseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Profile("generateData")
@Component("MerchandiseDataGenerator")
public class MerchandiseDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  //  private final MerchandiseRepository merchandiseRepository;




}
