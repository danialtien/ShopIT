package com.trainh.assignmentprm.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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

    List<ProductDTO> productComputer;
    RecyclerView rvComputer;
    ProductAdapter2 productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        rvComputer = findViewById(R.id.rvComputer);
        LinearLayoutManager linearLayoutManagerComputer = new LinearLayoutManager(this);
        linearLayoutManagerComputer.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvComputer.setLayoutManager(linearLayoutManagerComputer);

        productComputer = new ArrayList<>();
        callApiGetProduct();
    }

    private void callApiGetProduct(){

        ProductRepository.getService().getAll().enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(Call<List<ProductDTO>> call, Response<List<ProductDTO>> response) {
                productComputer = response.body();
                productAdapter = new ProductAdapter2(productComputer, new IClickProductListener() {
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
        Intent intent = new Intent(this, MyDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product", dto);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}