package com.trainh.assignmentprm.repository;

import com.trainh.assignmentprm.config.APIClient;
import com.trainh.assignmentprm.services.OrderService;

import retrofit2.Retrofit;

public class OrderRepository {
    public static OrderService getService() {
        Retrofit retrofit = APIClient.getClient();
        return retrofit.create(OrderService.class);
    }
}
