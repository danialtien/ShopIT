package com.trainh.assignmentprm.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.activity.adapter.CartAdapter2;
import com.trainh.assignmentprm.adapter.CartAdapter;
import com.trainh.assignmentprm.database.Database;
import com.trainh.assignmentprm.entities.Product;
import com.trainh.assignmentprm.model.OrderDetailDTO;
import com.trainh.assignmentprm.model.OrdersDTO;
import com.trainh.assignmentprm.model.PaymentDTO;
import com.trainh.assignmentprm.model.ProductDTO;
import com.trainh.assignmentprm.repository.OrderDetailRepository;
import com.trainh.assignmentprm.repository.OrderRepository;
import com.trainh.assignmentprm.repository.PaymentRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import retrofit2.Call;
import retrofit2.Callback;

public class MyCartActivity extends AppCompatActivity implements CartAdapter2.SelectedProduct {

    CartAdapter2 cartAdapter;
    RecyclerView rvCart;
    List<ProductDTO> productList;
    static List<OrderDetailDTO> detailDTOList;
    public static TextView txtMoney;
    Button btnThanhToan;
    String PublishableKey = "pk_test_51NQtVQFmQYmIg1cd39ZYMRbaXk8GgeTqlXB5mqrDUlWxTSCIjXUZEK4TGX8ywOxGUX9weXOPC2JqKaHPr58thmiV00t8Sg69Cg";
    String SecretKey = "sk_test_51NQtVQFmQYmIg1cdI1P7W71mE8Y0C8SXQGvuxzVqPdALltwNaZngMadOUx1RPgtFKoQWSoBflvjzJVBBNz1P5qvO0009NpsBIB";
    String CustomerId;
    String EphericalKey;
    String ClientSecret;
    PaymentSheet paymentSheet;

    public BigDecimal TongThanhToan;

    boolean isPay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        rvCart = (RecyclerView) findViewById(R.id.rvCart);
        txtMoney = (TextView) findViewById(R.id.txtMoney);
        btnThanhToan = (Button) findViewById(R.id.btnthanhtoan);
        LinearLayoutManager linearLayoutManagerCart = new LinearLayoutManager(this);
        linearLayoutManagerCart.setOrientation(LinearLayoutManager.VERTICAL);

        rvCart.setLayoutManager(linearLayoutManagerCart);
        productList = getProductComputer();
        detailDTOList = MyHomeActivity.orderDetailDTO;
        txtMoney.setText(formatter.format(TongTien()));
        cartAdapter = new CartAdapter2(productList, detailDTOList, this);
        rvCart.setAdapter(cartAdapter);

        isPay = false;
        //Payment
        PaymentConfiguration.init(this, PublishableKey);
        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
            if (isPay) {
                paymentSuccess();
                MyHomeActivity.loadOrderAPI(MyMainActivity.customerDTO.getId());
                Intent intent = new Intent(MyCartActivity.this, MyHomeActivity.class);
                startActivity(intent);
            }
        });



        if(detailDTOList == null){
            Intent intent = new Intent(MyCartActivity.this, MyHomeActivity.class);
            startActivity(intent);
        }

        TongThanhToan = TongTien();
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentFlow();
            }
        });

        if (detailDTOList != null) {
            StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject object = null;
                            try {
                                object = new JSONObject(response);
                                CustomerId = object.getString("id");
                                getEmphericalKey();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MyCartActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("Authorization", "Bearer " + SecretKey);
                    return header;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }

    }

    private void paymentFlow() {
        paymentSheet.presentWithPaymentIntent(ClientSecret,
                new PaymentSheet.Configuration("Thanh toan", new PaymentSheet.CustomerConfiguration(
                        CustomerId, EphericalKey
                )));
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            isPay = true;
            Toast.makeText(this, "Payment SuccessFul", Toast.LENGTH_SHORT).show();

        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show();

        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            // Payment failed
        }
    }

    private void getEmphericalKey() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            EphericalKey = object.getString("id");
                            getClientSecret(CustomerId, EphericalKey);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyCartActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);
                header.put("Stripe-Version", "2022-11-15");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getClientSecret(String customerId, String ephericalKey) {
        if (MyMainActivity.customerDTO != null) {
            MyHomeActivity.loadOrderAPI(MyMainActivity.customerDTO.getId());
            detailDTOList = MyHomeActivity.orderDetailDTO;
            if (TongThanhToan == null || detailDTOList == null) {
                Intent intent = new Intent(MyCartActivity.this, MyHomeActivity.class);
                startActivity(intent);
                return;
            }
        }

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    ClientSecret = object.getString("client_secret");
//                    Toast.makeText(CartActivity.this, ClientSecret, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MyCartActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SecretKey);
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", CustomerId);
                params.put("amount", String.valueOf(TongThanhToan.multiply(BigDecimal.valueOf(100)).intValue()));
                params.put("currency", "USD");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private List<ProductDTO> getProductComputer() {
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        OrdersDTO orders = MyHomeActivity.ordersDTO;
        List<OrderDetailDTO> odList = MyHomeActivity.orderDetailDTO;
        List id = new ArrayList();
        if (odList != null) {
            odList.stream().forEach(i -> id.add(i.getProductId()));
            MyHomeActivity.productDTOList.stream().forEach(dto -> {
                if (id.contains(dto.getId())) {
                    products.add(dto);
                }
            });
        }
        return products;
    }

    public BigDecimal TongTien() {
        detailDTOList = MyHomeActivity.orderDetailDTO;
        BigDecimal total = BigDecimal.valueOf(0);
        if (detailDTOList != null) {
            for (int i = 0; i < detailDTOList.size(); i++) {
                total = BigDecimal.valueOf(detailDTOList.get(i).getTotal().doubleValue()).add(total);
            }
        }
        return total;
    }

    @Override
    public void selectedProduct(ProductDTO product) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void paymentSuccess() {
        LocalDate currentDate;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);
            PaymentDTO dto = new PaymentDTO(null, MyHomeActivity.ordersDTO.getId(), formattedDate, TongTien(), "Card");
            PaymentRepository.getService().create(dto).enqueue(new Callback<PaymentDTO>() {
                @Override
                public void onResponse(Call<PaymentDTO> call, retrofit2.Response<PaymentDTO> response) {
                    updateOrder();
                }

                @Override
                public void onFailure(Call<PaymentDTO> call, Throwable t) {
                    Toast.makeText(MyCartActivity.this, "Payment Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateOrder() {
        OrdersDTO dto = MyHomeActivity.ordersDTO;
        if (dto != null) {
            dto.setStatus("Success");
            OrderRepository.getService().update(dto.getId(), dto).enqueue(new Callback<OrdersDTO>() {
                @Override
                public void onResponse(Call<OrdersDTO> call, retrofit2.Response<OrdersDTO> response) {
                    Log.i("Reponse Update Order Message", response.message());
                    if (response.isSuccessful()) {
//                        Toast.makeText(MyCartActivity.this, "Update Order Success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OrdersDTO> call, Throwable t) {

                }
            });
        }
    }
}