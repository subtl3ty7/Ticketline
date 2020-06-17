package at.ac.tuwien.sepm.groupphase.backend.datagenerator;


import at.ac.tuwien.sepm.groupphase.backend.entity.AbstractUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.Customer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;
import at.ac.tuwien.sepm.groupphase.backend.repository.MerchandiseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.MerchandiseService;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import at.ac.tuwien.sepm.groupphase.backend.util.Resources;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Profile("generateData")
@Component("MerchandiseDataGenerator")
@DependsOn({"UserDataGenerator"})
public class MerchandiseDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final MerchandiseRepository merchandiseRepository;
    private final MerchandiseService merchandiseService;
    private final UserService userService;
    private final EntityManagerFactory entityManagerFactory;
    private final Resources resources;

    private final static int NUMBER_OF_MERCHANDISE_PRODUCTS = 10;

    @Autowired
    public MerchandiseDataGenerator(MerchandiseRepository merchandiseRepository, MerchandiseService merchandiseService, UserService userService, EntityManagerFactory entityManagerFactory, Resources resources) {
        this.merchandiseRepository = merchandiseRepository;
        this.merchandiseService = merchandiseService;
        this.userService = userService;
        this.entityManagerFactory = entityManagerFactory;
        this.resources = resources;
    }


    private Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @PostConstruct
    public void generate() {
        if (merchandiseRepository.findAll().size() > 0) {
            LOGGER.info("Merchandise Test Data already generated");
        } else {
            LOGGER.info("Generating Merchandise Test Data...");
            LocalDateTime start = LocalDateTime.now();
            generateMerchandiseProducts();
            LocalDateTime end = LocalDateTime.now();
            float runningTime = Duration.between(start, end).toMillis();
            LOGGER.info("Generating Merchandise Test Data (" + NUMBER_OF_MERCHANDISE_PRODUCTS + " Entities) took " + runningTime / 1000.0 + " seconds");
        }
    }

    private List<Merchandise> generateMerchandiseProducts() {
        List<Merchandise> merchandiseProducts = new ArrayList<>();
        List<AbstractUser> users = userService.loadAllUsers();
        Customer customer0 = (Customer) users.get(0);

        for(int i = 0; i < NUMBER_OF_MERCHANDISE_PRODUCTS; i++) {


            String imgName = "merch_img" + i + ".jpg";


            Merchandise product = Merchandise.builder()
                .merchandiseProductCode("M1234" + i)
                .merchandiseProductName("Product" + i)
                .photo(resources.getImageEncoded(imgName))
                .stockCount(50)
                .price(10 * i + 1)
                .premiumPrice(5 * i +1)
                .premium((i%2) == 0)
                .build();

            product = merchandiseService.createNewProduct(product);
            merchandiseProducts.add(product);

            if((i % 2) == 0) {
                merchandiseService.purchaseWithMoney(product, customer0.getUserCode());
            }
        }

        return merchandiseProducts;
    }





















    }


