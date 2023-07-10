package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.OrdersDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderService {
    String ROOT = "order";

    @GET(ROOT + "/all")
    Call<OrdersDTO[]> getAll();

    @GET(ROOT + "/{id}")
    Call<OrdersDTO> getById(@Path("id") int id);

    @POST(ROOT + "")
    Call<OrdersDTO> create(@Body OrdersDTO dto);

    @PUT(ROOT + "/{id}")
    Call<OrdersDTO> update(@Path("id") int id, OrdersDTO dto);

    @DELETE(ROOT + "/{id}")
    Call<OrdersDTO> delete(@Path("id") int id);
}
