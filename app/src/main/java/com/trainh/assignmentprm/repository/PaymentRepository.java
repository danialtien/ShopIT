package com.trainh.assignmentprm.repository;

import com.trainh.assignmentprm.config.APIClient;
import com.trainh.assignmentprm.services.PaymentService;
import com.trainh.assignmentprm.services.ProductService;

import retrofit2.Retrofit;

public class PaymentRepository {
    public static PaymentService getService() {
        Retrofit retrofit = APIClient.getClient();
        return retrofit.create(PaymentService.class);
    }
}
