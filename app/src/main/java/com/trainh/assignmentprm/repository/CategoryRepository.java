package com.trainh.assignmentprm.repository;

import com.trainh.assignmentprm.config.APIClient;
import com.trainh.assignmentprm.services.CategoryService;
import com.trainh.assignmentprm.services.ProductService;

import retrofit2.Retrofit;

public class CategoryRepository {
    public static CategoryService getService() {
        Retrofit retrofit = APIClient.getClient();
        return retrofit.create(CategoryService.class);
    }
}
