package com.trainh.assignmentprm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.trainh.assignmentprm.adapter.CartAdapter;
import com.trainh.assignmentprm.adapter.ProductAdapter;
import com.trainh.assignmentprm.database.Database;
import com.trainh.assignmentprm.entities.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.SelectedProduct{

    Database database;
    CartAdapter cartAdapter;
    RecyclerView rvCart;
    List<Product> productList;
    TextView txtMoney;
    Button btnThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        database = new Database(getApplicationContext());
        rvCart = (RecyclerView) findViewById(R.id.rvCart);
        txtMoney =(TextView) findViewById(R.id.txtMoney);
        btnThanhToan = (Button) findViewById(R.id.btnthanhtoan);
        LinearLayoutManager linearLayoutManagerCart = new LinearLayoutManager(this);
        linearLayoutManagerCart.setOrientation(LinearLayoutManager.VERTICAL);

        rvCart.setLayoutManager(linearLayoutManagerCart);
        productList = getProductComputer();
        txtMoney.setText(formatter.format(TongTien()));
        cartAdapter = new CartAdapter(productList, this);
        rvCart.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private List<Product> getProductComputer() {
        List<Product> products = new ArrayList<Product>();
        Cursor dataProduct = database.GetData("SELECT cart.id, product.id, product.image, product.name, product.price, cart.quantity FROM cart INNER JOIN product ON cart.idProduct = product.id");
        while (dataProduct.moveToNext()) {
            Product product = new Product(dataProduct.getInt(0), dataProduct.getInt(1), dataProduct.getInt(2), dataProduct.getString(3), dataProduct.getDouble(4), dataProduct.getInt(5));
            Log.d("product", dataProduct.getString(2));
            products.add(product);
        }
        return products;
    }

    private double TongTien(){
        double total = 0;
        for(int i = 0; i < productList.size();i++){

            total += productList.get(i).getPrice();
        }
        return total;
    }

    @Override
    public void selectedProduct(Product product) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}