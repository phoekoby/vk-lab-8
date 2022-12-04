package org.example.web.rest;


import com.google.inject.Inject;
import org.example.dao.ProductDAO;
import org.example.pojo.ProductDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

@Path("/products")
public class ProductREST {

    private final ProductDAO productDAO;

    @Inject
    public ProductREST(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts(){
        Collection<ProductDTO> result = productDAO.getAll();
        return Response
                .ok(result)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewProduct(ProductDTO productDTO){
        productDTO = productDAO.save(productDTO);
        return Response
                .ok(productDTO)
                .build();
    }

    @Path("/delete/{name}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProductByName(@PathParam("name") String name){
        Optional<ProductDTO> productDTO = productDAO.findByName(name);
        if(productDTO.isPresent()){
            productDAO.delete(productDTO.get().getId());
            return Response
                    .ok()
                    .build();
        }else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProductsByOrganization(@QueryParam("organization") String organizationName){
        Collection<ProductDTO> products = productDAO.findAllByOrganizationName(organizationName);
        return Response
                .ok(products)
                .build();
    }
}
