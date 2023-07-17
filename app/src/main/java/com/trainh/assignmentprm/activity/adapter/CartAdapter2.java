package com.trainh.assignmentprm.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trainh.assignmentprm.CartActivity;
import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.adapter.CartAdapter;
import com.trainh.assignmentprm.database.Database;
import com.trainh.assignmentprm.entities.Product;
import com.trainh.assignmentprm.model.ProductDTO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter2 extends RecyclerView.Adapter<CartAdapter2.CartAdapterVh> {

    private List<ProductDTO> productList;
    private List<ProductDTO> getProductListFiltered;
    private Context context;
    public CartAdapter2.SelectedProduct selectedProduct;

    public CartAdapter2(List<ProductDTO> productList, CartAdapter2.SelectedProduct selectedProduct) {
        this.productList = productList;
        this.getProductListFiltered = productList;
        this.selectedProduct = selectedProduct;
    }

    @NonNull
    @Override
    public CartAdapter2.CartAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CartAdapter2.CartAdapterVh(LayoutInflater.from(context).inflate(R.layout.row_cart,null));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter2.CartAdapterVh holder, int position) {

        ProductDTO product = productList.get(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.cartName.setText(product.getProductName());
        holder.cartPrice.setText(formatter.format(product.getUnitPrice()));

        String imageUrl = product.getUrlImage();
        Picasso.get().load(imageUrl).into(holder.item_giohang_image);

        holder.cartQuantity.setText(String.valueOf(product.getUnitInStock()));
        holder.cartTotal.setText(formatter.format(product.getUnitPrice())); // Update total
        holder.bntDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Database database;
//                database = new Database(context.getApplicationContext());
//                database.QueryData("DELETE FROM cart WHERE id = " + product.getIdCart());
//
//                ((CartActivity)context).finish();
//                Intent intent = new Intent((CartActivity)context, CartActivity.class);
//                ((CartActivity)context).startActivity(intent);
            }
        });
        holder.bntTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Database database;
//                database = new Database(context.getApplicationContext());
//                if(product.getQuantity() == 1) {
//                    database.QueryData("DELETE FROM cart WHERE id = " + product.getIdCart());
//                } else {
//                    database.QueryData("UPDATE cart SET quantity = "+ Integer.valueOf(product.getQuantity() - 1)  +" WHERE id = " + product.getIdCart());
//                }
//                ((CartActivity)context).finish();
//                Intent intent = new Intent((CartActivity)context, CartActivity.class);
//                ((CartActivity)context).startActivity(intent);
            }
        });
        holder.bntCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Database database;
//                database = new Database(context.getApplicationContext());
//                database.QueryData("UPDATE cart SET quantity = "+ Integer.valueOf(product.getQuantity() + 1)  +" WHERE id = " + product.getIdCart());
//                ((CartActivity)context).finish();
//                Intent intent = new Intent((CartActivity)context, CartActivity.class);
//                ((CartActivity)context).startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

//    @Override
//    public Filter getFilter() {
//        return null;
//    }


    public interface SelectedProduct{

        void selectedProduct(ProductDTO product);

    }

    public class CartAdapterVh extends RecyclerView.ViewHolder {

        ImageView item_giohang_image;
        TextView cartName;
        TextView cartPrice;
        TextView cartQuantity;
        TextView cartTotal;
        Button bntDelete;

        ImageView bntTru;
        ImageView bntCong;


        ImageView imIcon;
        public CartAdapterVh(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            cartName = itemView.findViewById(R.id.cartName);
            cartPrice = itemView.findViewById(R.id.cartPrice);
            cartQuantity = itemView.findViewById(R.id.cartQuantity);
            cartTotal = itemView.findViewById(R.id.cartTotal);
            bntDelete = itemView.findViewById(R.id.bntDelete);

            bntTru = itemView.findViewById(R.id.bntTru);
            bntCong = itemView.findViewById(R.id.bntCong);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedProduct.selectedProduct(productList.get(getAdapterPosition()));
                }
            });
        }
    }
}