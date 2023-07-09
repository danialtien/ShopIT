package com.trainh.assignmentprm.repository;

import com.trainh.assignmentprm.config.APIClient;
import com.trainh.assignmentprm.services.OrderService;
import com.trainh.assignmentprm.services.ProductService;

import retrofit2.Retrofit;

public class OrdersRepository {
    public static OrderService getService() {
        Retrofit retrofit = APIClient.getClient();
        return retrofit.create(OrderService.class);
    }
}
