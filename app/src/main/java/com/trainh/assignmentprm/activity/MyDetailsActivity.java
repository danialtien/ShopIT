package com.trainh.assignmentprm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.trainh.assignmentprm.DetailsActivity;
import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.database.Database;
import com.trainh.assignmentprm.entities.Product;
import com.trainh.assignmentprm.model.ProductDTO;

import java.text.DecimalFormat;

public class MyDetailsActivity extends AppCompatActivity {

    ImageView ivProduct;
    TextView tvName;
    TextView tvPrice;
    TextView tvDescription;
    Button bntSMS;
    Button bntAddToCart;

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

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }

        ProductDTO dto = (ProductDTO) bundle.get("object_product");
        String imageUrl = dto.getUrlImage();
        Picasso.get().load(imageUrl).into(ivProduct);
        tvName.setText(dto.getProductName());
        tvPrice.setText(formatter.format(dto.getUnitPrice()) + " USD");
        tvDescription.setText(String.valueOf(dto.getDescription()));

//        Intent intent =getIntent();
////        if(intent.getExtras() != null){
////            Product product = (Product) intent.getSerializableExtra("data");
////
////            tvName.setText(product.getName());
////            tvPrice.setText(formatter.format(product.getPrice()) + " USD");
////            tvDescription.setText(String.valueOf(product.getDescription()));
////        }



        bntSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "0374192404";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
            }
        });

    }

}
