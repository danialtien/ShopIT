package com.trainh.assignmentprm.repository;

import com.trainh.assignmentprm.config.APIClient;
import com.trainh.assignmentprm.services.ProductService;
import com.trainh.assignmentprm.services.ShopService;

import retrofit2.Retrofit;

public class ShopRepository {
    public static ShopService getService() {
        Retrofit retrofit = APIClient.getClient();
        return retrofit.create(ShopService.class);
    }
}
