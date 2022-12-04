package org.example.dao;

import org.example.pojo.OrganizationDTO;

import java.util.Optional;

public interface OrganizationDAO extends CrudDAO<OrganizationDTO, Long> {
    Optional<OrganizationDTO> getByName(String name);
}
