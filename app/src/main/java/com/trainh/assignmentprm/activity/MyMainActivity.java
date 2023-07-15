package com.trainh.assignmentprm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.Register;
import com.trainh.assignmentprm.model.CustomerDTO;
import com.trainh.assignmentprm.model.User;
import com.trainh.assignmentprm.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMainActivity extends AppCompatActivity {

    EditText edUsername;
    EditText edPassword;
    Button btLogin;
    Button btRegisterPage;

    public static List<CustomerDTO> listCustomer;

    static CustomerDTO customerDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);

        edUsername = (EditText) findViewById(R.id.etUsername);
        edPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegisterPage = (Button) findViewById(R.id.btRegisterPage);

        listCustomer = new ArrayList<>();
        getListUsers();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCallAPI(new User(edUsername.getText().toString().trim(), edPassword.getText().toString().trim()));
            }
        });

        btRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyRegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginCallAPI(User user) {
        // Show loading indicator
        ProgressDialog progressDialog = ProgressDialog.show(MyMainActivity.this, "Logging in", "Please wait...", true);

        CustomerRepository.getService().login(user).enqueue(new Callback<CustomerDTO>() {
            @Override
            public void onResponse(Call<CustomerDTO> call, Response<CustomerDTO> response) {
                // Dismiss loading indicator
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    customerDTO = response.body();
                    if (customerDTO != null) {
                        Intent intent = new Intent(MyMainActivity.this, MyHomeActivity.class);
                        intent.putExtra("username", customerDTO.getFullName());
                        intent.putExtra("customerid", customerDTO.getId());
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MyMainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerDTO> call, Throwable t) {
                // Dismiss loading indicator
                progressDialog.dismiss();
                Log.e("Login User Error", t.toString());
                Toast.makeText(MyMainActivity.this, "Can not connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListUsers(){
        CustomerRepository.getService().getAll().enqueue(new Callback<List<CustomerDTO>>() {
            @Override
            public void onResponse(Call<List<CustomerDTO>> call, Response<List<CustomerDTO>> response) {
                listCustomer = response.body();
            }

            @Override
            public void onFailure(Call<List<CustomerDTO>> call, Throwable t) {
                Log.e("Get All User Error", t.toString());
            }
        });
    }

}