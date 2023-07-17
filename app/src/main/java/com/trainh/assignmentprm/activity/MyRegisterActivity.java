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

import com.trainh.assignmentprm.MainActivity;
import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.Register;
import com.trainh.assignmentprm.entities.Account;
import com.trainh.assignmentprm.model.CustomerDTO;
import com.trainh.assignmentprm.model.User;
import com.trainh.assignmentprm.repository.CustomerRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRegisterActivity extends AppCompatActivity {

    EditText et_username, et_fullname;
    EditText et_password;
    EditText et_confirm_password;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_register);

        et_fullname = (EditText) findViewById(R.id.et_fullname);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);

        btnRegister = (Button) findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = et_fullname.getText().toString();
                String email = et_username.getText().toString();
                String password = et_password.getText().toString();
                String confirmPassword = et_confirm_password.getText().toString();
                if(!password.trim().equals(confirmPassword.trim())){
                    Toast.makeText(MyRegisterActivity.this, "Password must be the same", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fullName.isEmpty() || email.isEmpty() || email.contains(" ") || password.isEmpty() || password.contains(" ") || confirmPassword.isEmpty() || confirmPassword.contains(" ")) {
                    Toast.makeText(MyRegisterActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                } else {
                    long count = MyMainActivity.listCustomer.stream().filter((dto) -> dto.getEmail().equals(email)).count();
                    if (count == 0) {
                        User user = new User(fullName, email, password);
                        clickRegister(user);
                    } else {
                        Toast.makeText(MyRegisterActivity.this, "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void clickRegister(User customer){
        ProgressDialog progressDialog = ProgressDialog.show(MyRegisterActivity.this, "Logging in", "Please wait...", true);

        CustomerRepository.getService().register(customer).enqueue(new Callback<CustomerDTO>() {
            @Override
            public void onResponse(Call<CustomerDTO> call, Response<CustomerDTO> response) {
                Log.i("New customer", response.body().toString());
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    CustomerDTO customerDTO = response.body();
                    Log.i("Register user", response.body().toString());
                    Log.i("Status user", response.isSuccessful() + " ");

                    if (customerDTO != null) {
                        Toast.makeText(MyRegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MyRegisterActivity.this, MyHomeActivity.class);
                        intent.putExtra("username", customerDTO.getFullName());
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerDTO> call, Throwable t) {
                Toast.makeText(MyRegisterActivity.this, "Call API register error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}