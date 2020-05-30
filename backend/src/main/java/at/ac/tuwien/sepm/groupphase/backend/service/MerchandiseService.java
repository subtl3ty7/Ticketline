package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Merchandise;

import java.util.List;

public interface MerchandiseService {

    Merchandise createNewProduct(Merchandise product);

    List<Merchandise> findAllMerchandiseProducts();


}
