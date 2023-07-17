package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.PaymentDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PaymentService {
    String ROOT = "payment";

    @GET(ROOT + "/all")
    Call<PaymentDTO[]> getAll();

    @GET(ROOT + "/{id}")
    Call<PaymentDTO> getById(@Path("id") int id);

    @POST(ROOT + "")
    Call<PaymentDTO> create(@Body PaymentDTO dto);

    @PUT(ROOT + "/{id}")
    Call<PaymentDTO> update(@Path("id") int id, PaymentDTO dto);

    @DELETE(ROOT + "/{id}")
    Call<PaymentDTO> delete(@Path("id") int id);
}
