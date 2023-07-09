package com.trainh.assignmentprm.repository;

import com.trainh.assignmentprm.config.APIClient;
import com.trainh.assignmentprm.services.ProductService;

import retrofit2.Retrofit;

public class ProductRepository {
    public static ProductService getService() {
        return APIClient.getClient().create(ProductService.class);
    }
}
