package com.trainh.assignmentprm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trainh.assignmentprm.activity.MyHomeActivity;
import com.trainh.assignmentprm.database.Database;
import com.trainh.assignmentprm.entities.Account;
import com.trainh.assignmentprm.entities.Product;

public class MainActivity extends AppCompatActivity {

    Database database;
    EditText edUsername;
    EditText edPassword;
    Button btLogin;
    Button btRegisterPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(getApplicationContext());

        edUsername = (EditText) findViewById(R.id.etUsername);
        edPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegisterPage = (Button) findViewById(R.id.btRegisterPage);


        edUsername.setText("vuong");
        edPassword.setText("1");

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = checkLogin();
                if (username != null) {
                    SharedPreferences settings = getApplicationContext().getSharedPreferences("username", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", username);
                    editor.apply();
                    Intent intent = new Intent(v.getContext(), MyHomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });



        database.createAccount(new Account("vuong", "1"));
        database.createProduct(new Product(R.drawable.nitro5, "Laptop Gaming Acer Nitro 5 AN515 45 R6EV", 19490000, 50, "Máy tính", "Acer vừa ra mắt phiên bản mới nhất của dòng máy gaming Nitro 5 - Nitro 5 AN515-45 R6EV được trang bị bộ vi xử lý AMD Ryzen 5 5600H, card đồ họa Geforce GTX 1650 4GB và tốc độ làm mới 144Hz cho hiệu năng xử lí mạnh mẽ cùng với hỗ trợ bàn phím RGB cá tính giúp mang lại trải nghiệm chơi game tốt nhất. Nitro 5 AN515-45 tích hợp những “vũ khí” mới nhất. Với sự kết hợp từ CPU AMD Ryzen 5 5600H và VGA NVIDIA GeForce GTX 1650, AN515-45 sẽ cho hiệu năng xử lý mạnh mẽ để xử lý tốt các công việc đồ họa đơn giản trên các phần mềm chuyên dụng, tốc độ xử lý thông tin cũng tương đối nhanh và mượt mà."));
        database.createProduct(new Product(R.drawable.img_4, "Laptop HP Gaming VICTUS 16 d0200TX i7 ", 19490000,  50,"Máy tính", "Acer vừa ra mắt phiên bản mới nhất của dòng máy gaming Nitro 5 - Nitro 5 AN515-45 R6EV được trang bị bộ vi xử lý AMD Ryzen 5 5600H, card đồ họa Geforce GTX 1650 4GB và tốc độ làm mới 144Hz cho hiệu năng xử lí mạnh mẽ cùng với hỗ trợ bàn phím RGB cá tính giúp mang lại trải nghiệm chơi game tốt nhất. Nitro 5 AN515-45 tích hợp những “vũ khí” mới nhất. Với sự kết hợp từ CPU AMD Ryzen 5 5600H và VGA NVIDIA GeForce GTX 1650, AN515-45 sẽ cho hiệu năng xử lý mạnh mẽ để xử lý tốt các công việc đồ họa đơn giản trên các phần mềm chuyên dụng, tốc độ xử lý thông tin cũng tương đối nhanh và mượt mà."));
        database.createProduct(new Product(R.drawable.img_5, "Laptop Dell Latitude 5580 Core i5-7300U", 21990000, 50, "Máy tính", "Acer vừa ra mắt phiên bản mới nhất của dòng máy gaming Nitro 5 - Nitro 5 AN515-45 R6EV được trang bị bộ vi xử lý AMD Ryzen 5 5600H, card đồ họa Geforce GTX 1650 4GB và tốc độ làm mới 144Hz cho hiệu năng xử lí mạnh mẽ cùng với hỗ trợ bàn phím RGB cá tính giúp mang lại trải nghiệm chơi game tốt nhất. Nitro 5 AN515-45 tích hợp những “vũ khí” mới nhất. Với sự kết hợp từ CPU AMD Ryzen 5 5600H và VGA NVIDIA GeForce GTX 1650, AN515-45 sẽ cho hiệu năng xử lý mạnh mẽ để xử lý tốt các công việc đồ họa đơn giản trên các phần mềm chuyên dụng, tốc độ xử lý thông tin cũng tương đối nhanh và mượt mà."));
        database.createProduct(new Product(R.drawable.img_6, "Laptop ASUS Gaming FX506LH-HN002T", 22990000,  50,"Máy tính", "Acer vừa ra mắt phiên bản mới nhất của dòng máy gaming Nitro 5 - Nitro 5 AN515-45 R6EV được trang bị bộ vi xử lý AMD Ryzen 5 5600H, card đồ họa Geforce GTX 1650 4GB và tốc độ làm mới 144Hz cho hiệu năng xử lí mạnh mẽ cùng với hỗ trợ bàn phím RGB cá tính giúp mang lại trải nghiệm chơi game tốt nhất. Nitro 5 AN515-45 tích hợp những “vũ khí” mới nhất. Với sự kết hợp từ CPU AMD Ryzen 5 5600H và VGA NVIDIA GeForce GTX 1650, AN515-45 sẽ cho hiệu năng xử lý mạnh mẽ để xử lý tốt các công việc đồ họa đơn giản trên các phần mềm chuyên dụng, tốc độ xử lý thông tin cũng tương đối nhanh và mượt mà."));
        database.createProduct(new Product(R.drawable.img_7, "Laptop Lenovo Legion 5 15ACH6 82JW00JPVN", 23990000,  50,"Máy tính", "Acer vừa ra mắt phiên bản mới nhất của dòng máy gaming Nitro 5 - Nitro 5 AN515-45 R6EV được trang bị bộ vi xử lý AMD Ryzen 5 5600H, card đồ họa Geforce GTX 1650 4GB và tốc độ làm mới 144Hz cho hiệu năng xử lí mạnh mẽ cùng với hỗ trợ bàn phím RGB cá tính giúp mang lại trải nghiệm chơi game tốt nhất. Nitro 5 AN515-45 tích hợp những “vũ khí” mới nhất. Với sự kết hợp từ CPU AMD Ryzen 5 5600H và VGA NVIDIA GeForce GTX 1650, AN515-45 sẽ cho hiệu năng xử lý mạnh mẽ để xử lý tốt các công việc đồ họa đơn giản trên các phần mềm chuyên dụng, tốc độ xử lý thông tin cũng tương đối nhanh và mượt mà."));
        database.createProduct(new Product(R.drawable.keyboard, "Bàn phím cơ AKKO 3108 Plus Prunus Lannesiana", 1589000,  50,"Bàn phím", "Bàn phím Akko được thiết kế dựa trên phong cách của nhân vật hư cấu nổi tiếng Dracula. Sử dụng tông màu đen ma mị cho tổng thể thêm sự huyền bí. Cùng với đó là những keycap theo chủ đề Dracula làm điểm nhấn khi cần custom bàn phím. Thiết kế fullsize 87 phím hỗ trợ numpad cho mỗi lần nhập liệu số được tốt hơn. Phần case cũng được làm từ chất liệu cao cấp làm bệ đỡ an toàn cho những trường hợp va chạm ngoài ý muốn. Lớp lót tiêu âm (FOAM) EVA dày 3.5mm nằm giữa plate và PCB loại bỏ âm thanh rỗng và những tiếng ping gây khó chịu."));
        database.createProduct(new Product(R.drawable.img, "Bàn phím cơ DareU EK87 Mutiled Brown Switch", 159900, 50, "Bàn phím", "Bàn phím Akko được thiết kế dựa trên phong cách của nhân vật hư cấu nổi tiếng Dracula. Sử dụng tông màu đen ma mị cho tổng thể thêm sự huyền bí. Cùng với đó là những keycap theo chủ đề Dracula làm điểm nhấn khi cần custom bàn phím. Thiết kế fullsize 87 phím hỗ trợ numpad cho mỗi lần nhập liệu số được tốt hơn. Phần case cũng được làm từ chất liệu cao cấp làm bệ đỡ an toàn cho những trường hợp va chạm ngoài ý muốn. Lớp lót tiêu âm (FOAM) EVA dày 3.5mm nằm giữa plate và PCB loại bỏ âm thanh rỗng và những tiếng ping gây khó chịu."));
        database.createProduct(new Product(R.drawable.img_1, "Bàn phím cơ AKKO ACR87 Blue", 1699000,  50,"Bàn phím", "Bàn phím Akko được thiết kế dựa trên phong cách của nhân vật hư cấu nổi tiếng Dracula. Sử dụng tông màu đen ma mị cho tổng thể thêm sự huyền bí. Cùng với đó là những keycap theo chủ đề Dracula làm điểm nhấn khi cần custom bàn phím. Thiết kế fullsize 87 phím hỗ trợ numpad cho mỗi lần nhập liệu số được tốt hơn. Phần case cũng được làm từ chất liệu cao cấp làm bệ đỡ an toàn cho những trường hợp va chạm ngoài ý muốn. Lớp lót tiêu âm (FOAM) EVA dày 3.5mm nằm giữa plate và PCB loại bỏ âm thanh rỗng và những tiếng ping gây khó chịu."));
        database.createProduct(new Product(R.drawable.img_2, "Bàn phím cơ Bluetooth Keychron K2 Nhôm RGB", 2290000,  50,"Bàn phím", "Bàn phím Akko được thiết kế dựa trên phong cách của nhân vật hư cấu nổi tiếng Dracula. Sử dụng tông màu đen ma mị cho tổng thể thêm sự huyền bí. Cùng với đó là những keycap theo chủ đề Dracula làm điểm nhấn khi cần custom bàn phím. Thiết kế fullsize 87 phím hỗ trợ numpad cho mỗi lần nhập liệu số được tốt hơn. Phần case cũng được làm từ chất liệu cao cấp làm bệ đỡ an toàn cho những trường hợp va chạm ngoài ý muốn. Lớp lót tiêu âm (FOAM) EVA dày 3.5mm nằm giữa plate và PCB loại bỏ âm thanh rỗng và những tiếng ping gây khó chịu."));
        database.createProduct(new Product(R.drawable.img_3, "Bàn phím cơ Keychron K4 V2 - LED RGB", 289900, 50, "Bàn phím", "Bàn phím Akko được thiết kế dựa trên phong cách của nhân vật hư cấu nổi tiếng Dracula. Sử dụng tông màu đen ma mị cho tổng thể thêm sự huyền bí. Cùng với đó là những keycap theo chủ đề Dracula làm điểm nhấn khi cần custom bàn phím. Thiết kế fullsize 87 phím hỗ trợ numpad cho mỗi lần nhập liệu số được tốt hơn. Phần case cũng được làm từ chất liệu cao cấp làm bệ đỡ an toàn cho những trường hợp va chạm ngoài ý muốn. Lớp lót tiêu âm (FOAM) EVA dày 3.5mm nằm giữa plate và PCB loại bỏ âm thanh rỗng và những tiếng ping gây khó chịu."));


        Cursor dataAccount = database.GetData("SELECT * FROM account");

        while (dataAccount.moveToNext()) {
            Account account = new Account(dataAccount.getString(1), dataAccount.getString(2));
            Log.d("user", dataAccount.getString(0));
            Log.d("password", dataAccount.getString(1));
        }

    }

    private String checkLogin() {
        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();
        Cursor dataAccount = database.GetData("SELECT * FROM account WHERE username = '" + username + "'" + " AND " + "password = '" + password + "'");
        if (dataAccount.moveToFirst()) {
            SharedPreferences settings = getApplicationContext().getSharedPreferences("idUser", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("idUser", dataAccount.getString(0));
            editor.apply();
            return username;
        }
        return null;
    }
}