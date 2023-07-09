package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.ProductDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {
    String ROOT = "product";

    @GET(ROOT + "/all")
    Call<List<ProductDTO>> getAll();

    @GET(ROOT + "/{id}")
    Call<ProductDTO> getById(@Path("id") int id);

    @POST(ROOT + "")
    Call<ProductDTO> create(@Body ProductDTO dto);

    @PUT(ROOT + "/{id}")
    Call<ProductDTO> update(@Path("id") int id, ProductDTO dto);

    @DELETE(ROOT + "/{id}")
    Call<ProductDTO> delete(@Path("id") int id);
}
