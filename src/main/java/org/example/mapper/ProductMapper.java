package org.example.mapper;

import generated.tables.records.ProductRecord;
import org.example.pojo.ProductDTO;
import org.jooq.Record4;

public class ProductMapper implements Mapper<ProductRecord, ProductDTO> {
    @Override
    public ProductRecord toRecord(ProductDTO productDTO) {
        final ProductRecord productRecord = new ProductRecord();
        productRecord.setId(productDTO.getId());
        productRecord.setName(productDTO.getName());
        productRecord.setAmount(productDTO.getAmount());
        return productRecord;
    }

    @Override
    public ProductDTO toEntity(ProductRecord productRecord) {
        return new ProductDTO(productRecord.getId(), productRecord.getName(), null, productRecord.getAmount());
    }

    public ProductDTO toEntityWithOrganizationName(Record4<Long, String, Integer, String> productRecord){
        return new ProductDTO(productRecord.value1(), productRecord.value2(), productRecord.value4(), productRecord.value3());
    }
}
