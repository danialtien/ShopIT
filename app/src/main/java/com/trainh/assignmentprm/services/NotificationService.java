package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.NotificationDTO;
import com.trainh.assignmentprm.model.ProductDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NotificationService {
    String ROOT = "notification";

    @GET(ROOT + "/all")
    Call<NotificationDTO[]> getAll();

    @GET(ROOT + "/{id}")
    Call<NotificationDTO> getById(@Path("id") int id);

    @POST(ROOT + "")
    Call<NotificationDTO> create(@Body NotificationDTO dto);

    @PUT(ROOT + "/{id}")
    Call<NotificationDTO> update(@Path("id") int id, NotificationDTO dto);

    @DELETE(ROOT + "/{id}")
    Call<NotificationDTO> delete(@Path("id") int id);
}
