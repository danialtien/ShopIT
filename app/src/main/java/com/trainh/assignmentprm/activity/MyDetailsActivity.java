package com.trainh.assignmentprm.activity;

import static com.trainh.assignmentprm.activity.MyHomeActivity.loadOrderAPI;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.model.OrderDetailDTO;
import com.trainh.assignmentprm.model.OrdersDTO;
import com.trainh.assignmentprm.model.ProductDTO;
import com.trainh.assignmentprm.repository.OrderDetailRepository;
import com.trainh.assignmentprm.repository.OrderRepository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDetailsActivity extends AppCompatActivity {

    ImageView ivProduct;
    TextView tvName;
    TextView tvPrice;
    TextView tvDescription;
    Button bntSMS;
    Button bntAddToCart, btnBuyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        DecimalFormat formatter = new DecimalFormat("#,###,#");
        ivProduct = (ImageView) findViewById(R.id.imgProduct);
        tvName = (TextView) findViewById(R.id.tvdName);
        tvPrice = (TextView) findViewById(R.id.tvdPrice);
        tvDescription = (TextView) findViewById(R.id.tvdDescription);
        bntSMS = (Button) findViewById(R.id.bntSMS);
        bntAddToCart = (Button) findViewById(R.id.bntAddToCart);
        btnBuyNow = (Button) findViewById(R.id.bntBuyNow);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        ProductDTO dto = (ProductDTO) bundle.get("object_product");
        String imageUrl = dto.getUrlImage();
        Picasso.get().load(imageUrl).into(ivProduct);
        tvName.setText(dto.getProductName());
        tvPrice.setText(formatter.format(BigDecimal.valueOf(10).multiply(dto.getUnitPrice())) + " USD");
        tvDescription.setText(String.valueOf(dto.getDescription()));

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
                if (MyMainActivity.customerDTO != null) {
                    loadOrderAPI(MyMainActivity.customerDTO.getId());
                    AddToCart(dto);
                }
            }
        });

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCart(dto);
                MyHomeActivity.loadOrderAPI(MyMainActivity.customerDTO.getId());
                Intent intent = new Intent(MyDetailsActivity.this, MyCartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createOrder(OrdersDTO ordersDTO, ProductDTO dto) {
        OrderRepository.getService().create(ordersDTO).enqueue(new Callback<OrdersDTO>() {
            @Override
            public void onResponse(Call<OrdersDTO> call, Response<OrdersDTO> response) {
                if (response.isSuccessful()) {
                    createOrderDetail(new OrderDetailDTO(null, response.body().getId(), dto.getId(), 1, dto.getUnitPrice(), dto.getUnitPrice()));
                }
            }

            @Override
            public void onFailure(Call<OrdersDTO> call, Throwable t) {
                Toast.makeText(MyDetailsActivity.this, "Add Order Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetailRepository.getService().create(orderDetailDTO).enqueue(new Callback<OrderDetailDTO>() {
            @Override
            public void onResponse(Call<OrderDetailDTO> call, Response<OrderDetailDTO> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MyDetailsActivity.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailDTO> call, Throwable t) {
                Toast.makeText(MyDetailsActivity.this, "Add Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateQuantity(OrderDetailDTO detailDTO) {
        List<OrderDetailDTO> temp = new ArrayList<>();

        OrderDetailRepository.getService().update(detailDTO.getId(), detailDTO).enqueue(new Callback<OrderDetailDTO>() {
            @Override
            public void onResponse(Call<OrderDetailDTO> call, Response<OrderDetailDTO> response) {
                if (response.isSuccessful()) {
                    temp.add(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderDetailDTO> call, Throwable t) {

            }
        });

        loadOrderAPI(MyMainActivity.customerDTO.getId());
    }

    private OrderDetailDTO checkExists(int idProduct) {

        List<OrderDetailDTO> detailDTOList = MyHomeActivity.orderDetailDTO;
        if (detailDTOList != null) {
            List<OrderDetailDTO> detailDTO = detailDTOList.stream().filter(x -> x.getProductId() == idProduct).collect(Collectors.toList());
            if (detailDTO.size() > 0) {
                return detailDTO.get(0);
            }
        }
        return null;
    }


    public void AddToCart(ProductDTO dto) {
        OrderDetailDTO tempDTO = checkExists(dto.getId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (tempDTO != null) {
                tempDTO.setQuantity(tempDTO.getQuantity() + 1);
                updateQuantity(tempDTO);
            } else {
                //                    TH1: Chua co Order
                if (MyHomeActivity.ordersDTO == null) {
                    LocalDate currentDate;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && MyMainActivity.customerDTO != null) {
                        currentDate = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String formattedDate = currentDate.format(formatter);
                        createOrder(new OrdersDTO(null, MyMainActivity.customerDTO.getId(), formattedDate, MyMainActivity.customerDTO.getAddress(), dto.getUnitPrice(), "Pending"), dto);
                    }
                } else { // co order chua co product
                    createOrderDetail(new OrderDetailDTO(null, MyHomeActivity.ordersDTO.getId(), dto.getId(), 1, dto.getUnitPrice(), dto.getUnitPrice()));
                }
            }
        }
        if (MyMainActivity.customerDTO != null) {
            loadOrderAPI(MyMainActivity.customerDTO.getId());
        }

    }

}
