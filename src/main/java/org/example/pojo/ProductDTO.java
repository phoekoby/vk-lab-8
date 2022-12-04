package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {
    @JsonProperty("id")
    private  Long id;
    @JsonProperty("name")
    private  String name;
    @JsonProperty("organization")
    private  String organizationName;
    @JsonProperty("amount")
    private  int amount;

    @JsonCreator
    public ProductDTO(@JsonProperty("id") Long id,
                      @JsonProperty("name") String name,
                      @JsonProperty("organization")String organizationName,
                      @JsonProperty("amount") int amount) {
        this.id = id;
        this.name = name;
        this.organizationName = organizationName;
        this.amount = amount;
    }

    public ProductDTO(){}

    public ProductDTO(String name, String organizationName, int amount) {
        this.id=null;
        this.name = name;
        this.organizationName = organizationName;
        this.amount = amount;
    }
}
