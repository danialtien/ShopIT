package com.trainh.assignmentprm.repository;

import com.trainh.assignmentprm.config.APIClient;
import com.trainh.assignmentprm.services.NotificationService;
import com.trainh.assignmentprm.services.ProductService;

import retrofit2.Retrofit;

public class NotificationRepository {
    public static NotificationService getService() {
        Retrofit retrofit = APIClient.getClient();
        return retrofit.create(NotificationService.class);
    }
}
