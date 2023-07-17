package com.trainh.assignmentprm.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
import com.trainh.assignmentprm.model.ProductDTO;
import com.trainh.assignmentprm.repository.OrderDetailRepository;
import com.trainh.assignmentprm.repository.OrderRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
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
    TextView txtMoney;
    Button btnThanhToan;

    String PublishableKey = "pk_test_51NQtVQFmQYmIg1cd39ZYMRbaXk8GgeTqlXB5mqrDUlWxTSCIjXUZEK4TGX8ywOxGUX9weXOPC2JqKaHPr58thmiV00t8Sg69Cg";
    String SecretKey = "sk_test_51NQtVQFmQYmIg1cdI1P7W71mE8Y0C8SXQGvuxzVqPdALltwNaZngMadOUx1RPgtFKoQWSoBflvjzJVBBNz1P5qvO0009NpsBIB";
    String CustomerId;
    String EphericalKey;
    String ClientSecret;

    PaymentSheet paymentSheet;

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
        txtMoney.setText(formatter.format(TongTien()));
        cartAdapter = new CartAdapter2(productList, this);
        rvCart.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        //Payment
        PaymentConfiguration.init(this, PublishableKey);
        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });


        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentFlow();
            }
        });


        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            CustomerId = object.getString("id");
//                    Toast.makeText(CartActivity.this, CustomerId, Toast.LENGTH_SHORT).show();
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

    private void paymentFlow() {
        paymentSheet.presentWithPaymentIntent(ClientSecret,
                new PaymentSheet.Configuration("Thanh toan", new PaymentSheet.CustomerConfiguration(
                        CustomerId, EphericalKey
                )));
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(CartActivity.this, EphericalKey, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MyCartActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("amount", "100" + "00");
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
        if(odList != null){
            odList.stream().forEach(i -> id.add(i.getProductId()));
            MyHomeActivity.productDTOList.stream().forEach(dto -> {
                if(id.contains(dto.getId())){
                    products.add(dto);
                }
            });
        }
        return products;
    }


    private double TongTien() {
        double total = 0;
        for (int i = 0; i < productList.size(); i++) {

            total += productList.get(i).getUnitPrice().doubleValue();
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
}