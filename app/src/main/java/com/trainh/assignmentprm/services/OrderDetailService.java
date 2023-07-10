package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.OrderDetailDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderDetailService {
    String ROOT = "orderDetail";

    @GET(ROOT + "/all")
    Call<OrderDetailDTO[]> getAll();

    @GET(ROOT + "/{id}")
    Call<OrderDetailDTO> getById(@Path("id") int id);

    @POST(ROOT + "")
    Call<OrderDetailDTO> create(@Body OrderDetailDTO dto);

    @PUT(ROOT + "/{id}")
    Call<OrderDetailDTO> update(@Path("id") int id, OrderDetailDTO dto);

    @DELETE(ROOT + "/{id}")
    Call<OrderDetailDTO> delete(@Path("id") int id);
}
