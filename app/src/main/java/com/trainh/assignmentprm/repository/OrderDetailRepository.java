package com.trainh.assignmentprm.repository;

import com.trainh.assignmentprm.config.APIClient;
import com.trainh.assignmentprm.services.OrderDetailService;
import com.trainh.assignmentprm.services.OrderService;

import retrofit2.Retrofit;

public class OrderDetailRepository {
    public static OrderDetailService getService() {
        Retrofit retrofit = APIClient.getClient();
        return retrofit.create(OrderDetailService.class);
    }
}
