package com.trainh.assignmentprm.repository;

import com.trainh.assignmentprm.config.APIClient;
import com.trainh.assignmentprm.services.OrderService;

import retrofit2.Retrofit;

public class OrderRepository {
    public static OrderService getService() {
        return APIClient.getClient().create(OrderService.class);
    }
}
