package com.trainh.assignmentprm;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.trainh.assignmentprm.database.Database;
import com.trainh.assignmentprm.entities.Product;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity {

    ImageView ivProduct;
    TextView tvName;
    TextView tvPrice;
    TextView tvDescription;
    Button bntSMS;
    Button bntAddToCart;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        ivProduct = (ImageView) findViewById(R.id.imgProduct);
        tvName = (TextView) findViewById(R.id.tvdName);
        tvPrice = (TextView) findViewById(R.id.tvdPrice);
        tvDescription = (TextView) findViewById(R.id.tvdDescription);
        bntSMS = (Button) findViewById(R.id.bntSMS);
        bntAddToCart = (Button) findViewById(R.id.bntAddToCart);
        database = new Database(getApplicationContext());

        Intent intent =getIntent();
        if(intent.getExtras() != null){
            Product product = (Product) intent.getSerializableExtra("data");
            ivProduct.setImageResource(product.getImage());
            tvName.setText(product.getName());
            tvPrice.setText(formatter.format(product.getPrice()) + " vnd");
            tvDescription.setText(String.valueOf(product.getDescription()));
        }



        bntSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "0374192404";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
            }
        });

        bntAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getApplicationContext().getSharedPreferences("idUser", 0);
                String idUser = settings.getString("idUser", "");
                Product product = (Product) intent.getSerializableExtra("data");
                int checkExists = checkExists(product.getId());
                if (checkExists != -1) {
                    updateQuantity(Integer.parseInt(idUser), product.getId(), checkExists + 1);
                    Toast.makeText(DetailsActivity.this, "Số lượng là: " + (1 + checkExists), Toast.LENGTH_SHORT).show();
                } else {
                    if(database.createCart(Integer.parseInt(idUser), product.getId())) {
                        Toast.makeText(DetailsActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailsActivity.this, "Thêm vào giỏ hàng không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
                Intent intent = new Intent(DetailsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
    private int checkExists(int idProduct) {
        database = new Database(getApplicationContext());
        Cursor cursor = database.GetData("SELECT * FROM cart WHERE idProduct = '" + idProduct + "'");
        if( cursor.moveToFirst() ) {
            return cursor.getInt(3);
        } else {
            return -1;
        }
    }
    private void updateQuantity(int idUser, int idProduct, int quantityC) {
        database.QueryData("UPDATE cart SET quantity = " + quantityC + " WHERE idProduct = " + idProduct + " AND idAccount = "+ idUser +"");
    }
}