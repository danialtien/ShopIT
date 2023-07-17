package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.ShopDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShopService {
    String ROOT = "shop";

    @GET(ROOT + "/all")
    Call<ShopDTO[]> getAll();

    @GET(ROOT + "/{id}")
    Call<ShopDTO> getById(@Path("id") int id);

    @POST(ROOT + "")
    Call<ShopDTO> create(@Body ShopDTO dto);

    @PUT(ROOT + "/{id}")
    Call<ShopDTO> update(@Path("id") int id, ShopDTO dto);

    @DELETE(ROOT + "/{id}")
    Call<ShopDTO> delete(@Path("id") int id);
}
