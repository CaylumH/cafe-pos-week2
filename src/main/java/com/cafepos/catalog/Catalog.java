package com.cafepos.catalog;

import java.util.Optional;
import com.cafepos.common.Product;

public interface Catalog {
    void add(Product p);
    Optional<Product> findById(String id);
}
