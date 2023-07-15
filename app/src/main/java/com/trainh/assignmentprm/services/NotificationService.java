package com.trainh.assignmentprm.services;

import com.trainh.assignmentprm.model.NotificationDTO;

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

public interface NotificationService {
    String ROOT = "notification";

    @Headers("Content-Type: application/json")
    @GET(ROOT + "/all")
    Call<NotificationDTO[]> getAll();

    @GET(ROOT + "/getNotificationByCustomerId")
    Call<List<NotificationDTO>> getNotificationByCustomerId(@Query("customerId") int customerId);

    @GET(ROOT + "/{id}")
    Call<NotificationDTO> getById(@Path("id") int id);

    @POST(ROOT + "")
    Call<NotificationDTO> create(@Body NotificationDTO dto);

    @PUT(ROOT + "/{id}")
    Call<NotificationDTO> update(@Path("id") int id, NotificationDTO dto);

    @DELETE(ROOT + "/{id}")
    Call<NotificationDTO> delete(@Path("id") int id);
}
