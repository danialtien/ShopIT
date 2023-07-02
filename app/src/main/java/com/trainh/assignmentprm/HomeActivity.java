package com.trainh.assignmentprm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trainh.assignmentprm.adapter.ProductAdapter;
import com.trainh.assignmentprm.database.Database;
import com.trainh.assignmentprm.entities.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ProductAdapter.SelectedProduct{

    Database database;
    TextView tvUsername;
    List<Product> productComputer;
    List<Product> productKeyboard;
    RecyclerView rvComputer;
    RecyclerView rvKeyboard;
    ProductAdapter productAdapterComputer;
    ProductAdapter productAdapterKeyboard;
    ImageView cart;
    TextView tvNoti;
    ImageView imgMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = new Database(getApplicationContext());

        SharedPreferences settings = getApplicationContext().getSharedPreferences("username", 0);
        String username = settings.getString("username", "username");
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setText(username);
        productComputer = getProductComputer();
        productKeyboard = getProductKeyboard();
        rvComputer = (RecyclerView) findViewById(R.id.rvComputer);
        rvKeyboard = (RecyclerView) findViewById(R.id.rvKeyboard);
        cart = (ImageView) findViewById(R.id.ivCart);
        tvNoti = (TextView) findViewById(R.id.tvNoti);
        ImageView imgMaps = findViewById(R.id.imageView2);


        LinearLayoutManager linearLayoutManagerComputer = new LinearLayoutManager(this);
        linearLayoutManagerComputer.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager linearLayoutManagerKeyboard = new LinearLayoutManager(this);
        linearLayoutManagerKeyboard.setOrientation(LinearLayoutManager.HORIZONTAL);


        rvComputer.setLayoutManager(linearLayoutManagerComputer);
        rvKeyboard.setLayoutManager(linearLayoutManagerKeyboard);

        productAdapterComputer = new ProductAdapter(productComputer,  this);

        productAdapterKeyboard = new ProductAdapter(productKeyboard,  this);

        rvComputer.setAdapter(productAdapterComputer);
        rvKeyboard.setAdapter(productAdapterKeyboard);

        tvNoti.setText(String.valueOf(getProduct().size()));

        imgMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/place/GEARVN+Ho%C3%A0ng+Hoa+Th%C3%A1m/@10.7989457,106.6452575,17z/data=!3m1!4b1!4m5!3m4!1s0x3175294a0c97a181:0x6aece518177f9a92!8m2!3d10.7989404!4d106.6474462"));
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<Product> getProduct() {
        List<Product> products = new ArrayList<Product>();
        Cursor dataProduct = database.GetData("SELECT cart.id, product.id, product.image, product.name, product.price, cart.quantity FROM cart INNER JOIN product ON cart.idProduct = product.id");
        while (dataProduct.moveToNext()) {
            Product product = new Product(dataProduct.getInt(0), dataProduct.getInt(1), dataProduct.getInt(2), dataProduct.getString(3), dataProduct.getDouble(4), dataProduct.getInt(5));
            Log.d("product", dataProduct.getString(2));
            products.add(product);
        }
        return products;
    }

    private List<Product> getProductComputer() {
        List<Product> products = new ArrayList<Product>();
        Cursor dataProduct = database.GetData("SELECT * FROM product WHERE type = 'Máy tính'");
        while (dataProduct.moveToNext()) {
            Product product = new Product(dataProduct.getInt(0), dataProduct.getInt(1), dataProduct.getString(2), dataProduct.getDouble(3), dataProduct.getInt(4) ,dataProduct.getString(5), dataProduct.getString(6));
            Log.d("product", dataProduct.getString(2));
            products.add(product);
        }
        return products;
    }

    private List<Product> getProductKeyboard() {
        List<Product> products = new ArrayList<Product>();
        Cursor dataProduct = database.GetData("SELECT * FROM product WHERE type = 'Bàn phím'");
        while (dataProduct.moveToNext()) {
            Product product = new Product(dataProduct.getInt(0), dataProduct.getInt(1), dataProduct.getString(2), dataProduct.getDouble(3),dataProduct.getInt(4),  dataProduct.getString(5), dataProduct.getString(6));
            Log.d("product", dataProduct.getString(2));
            products.add(product);
        }
        return products;
    }

    @Override
    public void selectedProduct(Product product) {
        Log.d("product", product.getName());
        Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", product);
        startActivity(intent);
//        startActivity(new Intent(HomeActivity.this, DetailsActivity.class).putExtra("data",  product));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}