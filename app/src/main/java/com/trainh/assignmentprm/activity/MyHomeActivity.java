package com.trainh.assignmentprm.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.trainh.assignmentprm.CartActivity;
import com.trainh.assignmentprm.HomeActivity;
import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.activity.adapter.ProductAdapter2;
import com.trainh.assignmentprm.activity.my_interface.IClickProductListener;
import com.trainh.assignmentprm.model.ProductDTO;
import com.trainh.assignmentprm.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyHomeActivity extends AppCompatActivity {

    List<ProductDTO> productDTOList;
    RecyclerView rvComputer;
    ProductAdapter2 productAdapter;
    TextView tvUsername;
    ImageView cart;
    TextView tvNoti;
    ImageView imgMaps;
    ImageView Notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvUsername.setText(username);

        rvComputer = findViewById(R.id.rvComputer);
        Notify = (ImageView) findViewById(R.id.ivNotification);

        LinearLayoutManager linearLayoutManagerComputer = new LinearLayoutManager(this);
        linearLayoutManagerComputer.setOrientation(LinearLayoutManager.VERTICAL);
        rvComputer.setLayoutManager(linearLayoutManagerComputer);
        cart = (ImageView) findViewById(R.id.ivCart);
        tvNoti = (TextView) findViewById(R.id.tvNoti);
        imgMaps = findViewById(R.id.imageView2);



        productDTOList = new ArrayList<>();
        callApiGetProduct();

        tvNoti.setText(String.valueOf(productDTOList.size()));

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
                Intent intent = new Intent(MyHomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        Notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int cudId = intent.getIntExtra("customerid", 0);
                Intent newIntent = new Intent(MyHomeActivity.this, MyNotificationActivity.class);
                newIntent.putExtra("customerid", cudId);
                startActivity(newIntent);
            }
        });
    }

    private void callApiGetProduct(){

        ProductRepository.getService().getAll().enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                productDTOList = response.body();
                productAdapter = new ProductAdapter2(productDTOList, new IClickProductListener() {
                    @Override
                    public void onClickItemProduct(ProductDTO dto) {
                        onClickGotoDetail(dto);
                    }
                });
                rvComputer.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductDTO>> call, Throwable t) {

            }
        });
    }

    private void onClickGotoDetail(ProductDTO dto) {
        Intent intent = new Intent(MyHomeActivity.this, MyDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product", dto);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
