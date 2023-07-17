package com.trainh.assignmentprm.repository;

import com.trainh.assignmentprm.config.APIClient;
import com.trainh.assignmentprm.services.CustomerService;

import retrofit2.Retrofit;

public class CustomerRepository {
    public static CustomerService getService() {
        Retrofit retrofit = APIClient.getClient();
        return retrofit.create(CustomerService.class);
    }
}
