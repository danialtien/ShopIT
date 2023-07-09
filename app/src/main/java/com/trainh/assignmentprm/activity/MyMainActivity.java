package com.trainh.assignmentprm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.model.CustomerDTO;
import com.trainh.assignmentprm.model.User;
import com.trainh.assignmentprm.repository.CustomerRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMainActivity extends AppCompatActivity {

    EditText edUsername;
    EditText edPassword;
    Button btLogin;
    Button btRegisterPage;

    static CustomerDTO customerDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_main);

        edUsername = (EditText) findViewById(R.id.etUsername);
        edPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegisterPage = (Button) findViewById(R.id.btRegisterPage);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edUsername.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                User user = null;
                if(!email.isEmpty() && !password.isEmpty()){
                    user = new User(email, password);
                } else {
                    Toast.makeText(MyMainActivity.this, "Please input all", Toast.LENGTH_SHORT).show();
                    return;
                }
                CustomerRepository.getService().login(user).enqueue(new Callback<CustomerDTO>() {
                    @Override
                    public void onResponse(Call<CustomerDTO> call, Response<CustomerDTO> response) {
                        customerDTO = response.body();
                        if(customerDTO != null){
                            Intent intent = new Intent(MyMainActivity.this, MyHomeActivity.class);
                            intent.putExtra("username", customerDTO.getFullName());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerDTO> call, Throwable t) {
                        Toast.makeText(MyMainActivity.this, "Can not find user", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}