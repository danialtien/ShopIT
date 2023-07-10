package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.CategoryDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryService {
    String ROOT = "category";

    @GET(ROOT + "/all")
    Call<CategoryDTO[]> getAll();

    @GET(ROOT + "/{id}")
    Call<CategoryDTO> getById(@Path("id") int id);

    @POST(ROOT + "")
    Call<CategoryDTO> create(@Body CategoryDTO dto);

    @PUT(ROOT + "/{id}")
    Call<CategoryDTO> update(@Path("id") int id, CategoryDTO dto);

    @DELETE(ROOT + "/{id}")
    Call<CategoryDTO> delete(@Path("id") int id);
}
