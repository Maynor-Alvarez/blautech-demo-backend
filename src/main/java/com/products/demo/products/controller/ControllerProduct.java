package com.products.demo.products.controller;

import com.products.demo.products.bo.BoProduct;
import com.products.demo.products.dto.DTOProduct;
import com.products.demo.products.service.ServiceProduct;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerProduct {

    private final ServiceProduct serviceProduct;

    public ControllerProduct(ServiceProduct serviceProduct) {
        this.serviceProduct = serviceProduct;
    }

    @GetMapping
    public ResponseEntity<Page<BoProduct>> findAll( @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) throws Exception {
        Page<BoProduct> pages = serviceProduct.getAll(page, size);
        return new ResponseEntity<>(pages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOProduct> findById(@PathVariable String id) throws Exception {
        DTOProduct product = serviceProduct.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DTOProduct> save(@RequestBody DTOProduct dto) throws Exception {

        DTOProduct savedProduct = serviceProduct.save(dto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    /*
    @PutMapping
    public ResponseEntity<DTOProduct> update(@RequestBody DTOProduct dto) throws Exception, NotFoundException {
        try {
            DTOProduct savedProduct = serviceProduct.update(dto);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    */

    @PutMapping
    public ResponseEntity<DTOProduct> update(@RequestBody DTOProduct dto) throws Exception {
        DTOProduct savedProduct = serviceProduct.update(dto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws Exception {
            serviceProduct.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

}
