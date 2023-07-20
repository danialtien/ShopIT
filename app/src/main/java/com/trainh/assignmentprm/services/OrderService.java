package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.OrdersDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderService {
    String ROOT = "order";

    @GET(ROOT + "/all")
    Call<OrdersDTO[]> getAll();

    @GET(ROOT + "/getOrdersByCustomerId")
    Call<OrdersDTO> getByCustomerId(@Query("customerId") Integer id);

    @POST(ROOT + "")
    Call<OrdersDTO> create(@Body OrdersDTO dto);

    @PUT(ROOT + "")
    Call<OrdersDTO> update(@Query("id") int id, @Body OrdersDTO dto);

    @DELETE(ROOT + "/{id}")
    Call<OrdersDTO> delete(@Path("id") int id);
}
