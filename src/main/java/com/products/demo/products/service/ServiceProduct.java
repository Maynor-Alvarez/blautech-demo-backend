package com.products.demo.products.service;

import com.products.demo.exceptions.NotFoundException;
import com.products.demo.products.bo.BoProduct;
import com.products.demo.products.dto.DTOProduct;
import com.products.demo.products.repository.RepositoryProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class ServiceProduct {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProduct.class);
    private final RepositoryProduct repositoryProduct;

    private final String error_not_found = "Producto no encontrado";
    private final String error_saved = "Error al guardar Producto";

    public ServiceProduct(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    public Page<BoProduct> getAll(Integer page, Integer size) throws Exception{
        try {
            Pageable pageable = PageRequest.of(page, size);
            return repositoryProduct.findAll(pageable);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public DTOProduct save(DTOProduct dto) throws Exception {
        try {
            BoProduct bo = new BoProduct();

            BeanUtils.copyProperties(dto, bo, "image");

            if (!dto.getImage().isEmpty()) {
                byte[] imageBytes = Base64.getDecoder().decode(dto.getImage());
                bo.setImage(imageBytes);
            }

            BoProduct saved = repositoryProduct.save(bo);

            dto.setId(saved.getId());

            return dto;
        } catch (Exception e) {
            LOGGER.error(error_saved);
            throw new Exception(error_saved + e.getMessage());
        }
    }

    public DTOProduct update(DTOProduct dto) throws Exception {
        try {
            if (getProduct(dto.getId()).isPresent()) {
                BoProduct bo = new BoProduct();

                BeanUtils.copyProperties(dto, bo, "image");
                bo.setImage(dto.getImage().getBytes());

                repositoryProduct.save(bo);
            } else {
                LOGGER.error(error_not_found);
                throw new NotFoundException(error_not_found);
            }

            return dto;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(error_saved);
            throw new Exception(error_saved + e.getMessage());
        }
    }

    public void delete(String id) throws Exception {
        try {
            if (getProduct(id).isPresent()) {
                repositoryProduct.deleteById(id);
            } else {
                LOGGER.error(error_not_found);
                throw new NotFoundException(error_not_found);
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public DTOProduct getById(String id) throws Exception {
        try {
            Optional<BoProduct> bo = getProduct(id);
            DTOProduct dto = new DTOProduct();

            if (bo.isEmpty()) {
                LOGGER.error(error_not_found);
                throw new NotFoundException(error_not_found);
            }

            BeanUtils.copyProperties(bo.get(), dto, "image");

            if (bo.get().getImage() != null) {
                String imageString = Base64.getEncoder().encodeToString(bo.get().getImage());
                dto.setImage(imageString);
            }

            return dto;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private Optional<BoProduct> getProduct(String id) {
        return repositoryProduct.findById(id);
    }
}
