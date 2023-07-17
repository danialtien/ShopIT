package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.CustomerDTO;
import com.trainh.assignmentprm.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerService {
    String ROOT = "customer";

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<CustomerDTO> login(@Body User user);

    @POST("register")
    Call<CustomerDTO> register(@Body User user);

    @GET(ROOT + "/all")
    Call<List<CustomerDTO>> getAll();

    @GET(ROOT + "/{id}")
    Call<CustomerDTO> getById(@Path("id") int id);

    @POST(ROOT)
    Call<CustomerDTO> create(@Body CustomerDTO dto);

    @PUT(ROOT + "/{id}")
    Call<CustomerDTO> update(@Path("id") int id, CustomerDTO dto);

    @DELETE(ROOT + "/{id}")
    Call<CustomerDTO> delete(@Path("id") int id);
}
