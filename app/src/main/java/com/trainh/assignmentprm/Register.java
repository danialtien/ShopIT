package com.trainh.assignmentprm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trainh.assignmentprm.database.Database;
import com.trainh.assignmentprm.entities.Account;

public class Register extends AppCompatActivity {

    Database database;
    EditText et_username;
    EditText et_password;
    EditText et_confirm_password;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = new Database(getApplicationContext());

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);

        btnRegister = (Button) findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                String confirmPassword = et_confirm_password.getText().toString();


//                if (username.isEmpty() || username.contains(" ")) {
//                    Toast.makeText(Register.this, "Tên tài khoản - Không được để trống hoặc chứa khoảng cách", Toast.LENGTH_SHORT).show();
//                }
//
//                if (password.isEmpty() || password.contains(" ")) {
//                    Toast.makeText(Register.this, "Mật khẩu - Không được để trống hoặc chứa khoảng cách", Toast.LENGTH_SHORT).show();
//                }
//
//                if (confirmPassword.isEmpty() || confirmPassword.contains(" ")) {
//                    Toast.makeText(Register.this, "Xác Nhận Mật khẩu- Không được để trống hoặc chứa khoảng cách", Toast.LENGTH_SHORT).show();
//                }
//
//                if (!password.equals(confirmPassword)) {
//                    Toast.makeText(Register.this, "Mật khẩu và Xác nhận mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
//                }

                if (username.isEmpty() || username.contains(" ") || password.isEmpty() || password.contains(" ") || confirmPassword.isEmpty() || confirmPassword.contains(" ") || !password.equals(confirmPassword)) {
                    Toast.makeText(Register.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkUsernameExisted = database.checkUsernameExisted(username);
                    if (checkUsernameExisted == false) {
                        boolean register = database.createAccount(new Account(username, password));
                        if (register == true) {
                            Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(Register.this, "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}