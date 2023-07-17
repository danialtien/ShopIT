package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.OrderDetailDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderDetailService {
    String ROOT = "order_details";

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

    @GET(ROOT + "/getOrderDetailsByOrderId")
    Call<List<OrderDetailDTO>> getOrderDetailByOrderID(@Query("orderId") Integer id);
}
