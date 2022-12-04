package org.example.dao;

import org.example.pojo.ProductDTO;

import java.util.Collection;
import java.util.Optional;


public interface ProductDAO extends CrudDAO<ProductDTO, Long> {
    Optional<ProductDTO> findByName(String name);

    Collection<ProductDTO> findAllByOrganizationName(String organizationName);
}
