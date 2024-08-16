package com.products.demo.products.repository;

import com.products.demo.products.bo.BoProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositoryProduct extends MongoRepository <BoProduct, String> {


}
