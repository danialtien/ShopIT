package com.trainh.assignmentprm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.adapter.NotificationAdapter;
import com.trainh.assignmentprm.config.NotificationHelper;
import com.trainh.assignmentprm.model.NotificationDTO;
import com.trainh.assignmentprm.repository.NotificationRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNotificationActivity extends AppCompatActivity {
    List<NotificationDTO> notificationList;
    RecyclerView recyclerView;
    ImageView ImgGoBack;
    int customerId;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification);

        Intent intent = getIntent();
        Mapping(intent);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetAllNotificationOfUser(customerId);
        GetTokenNotification();

        ImageView imgNotify = (ImageView) findViewById(R.id.img_notify);
        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationHelper.SendNotification(MyNotificationActivity.this,"You are order success ", "You are payment success 10 PC HP I4 Core 7x");
            }
        });
        ImgGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent  newIntent = new Intent(MyNotificationActivity.this, MyHomeActivity.class);
               newIntent.putExtra("username", username);
               startActivity(newIntent);
            }
        });
    }

    private void Mapping(Intent intent) {
        customerId = intent.getIntExtra("customerid", 0);
        username = intent.getStringExtra("username");
        notificationList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv_notification);
        ImgGoBack = (ImageView) findViewById(R.id.img_go_back);
    }

    private static void GetTokenNotification() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.println(Log.ERROR, "Error","Fetching FCM registration token failed" + task.getException());
                            return;
                        }

                        String token = task.getResult();
                        Log.println(Log.INFO, "Token",token);
                    }
                });
    }

    protected void GetAllNotificationOfUser(int customerId){
            NotificationRepository.getService().getNotificationByCustomerId(customerId).enqueue(new Callback<List<NotificationDTO>>() {
                @Override
                public void onResponse(Call<List<NotificationDTO>> call, Response<List<NotificationDTO>> response) {
                    if(response.isSuccessful()){
                        notificationList = response.body();
                        if(notificationList != null){
                            Collections.sort(notificationList, new Comparator<NotificationDTO>() {
                                @Override
                                public int compare(NotificationDTO n1, NotificationDTO n2) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        return n2.getCreatedAt().compareTo(n1.getCreatedAt());
                                    }
                                    return 0;
                                }
                            });

                            NotificationAdapter adapter = new NotificationAdapter(notificationList);
                            recyclerView.setAdapter(adapter);
                        }else{
                            Toast.makeText(MyNotificationActivity.this, "You have no notification left", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MyNotificationActivity.this, "Load notify failed please try back later", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<NotificationDTO>> call, Throwable t) {
                    Toast.makeText(MyNotificationActivity.this, "Error occurred " + t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
