package com.trainh.assignmentprm.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.activity.MyCartActivity;
import com.trainh.assignmentprm.activity.MyHomeActivity;
import com.trainh.assignmentprm.model.OrderDetailDTO;
import com.trainh.assignmentprm.model.ProductDTO;
import com.trainh.assignmentprm.repository.OrderDetailRepository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter2 extends RecyclerView.Adapter<CartAdapter2.CartAdapterVh> {

    private List<ProductDTO> productList;
    private List<ProductDTO> getProductListFiltered;

    private List<OrderDetailDTO> detailDTOList;
    private Context context;
    public CartAdapter2.SelectedProduct selectedProduct;

    public CartAdapter2(List<ProductDTO> productList, List<OrderDetailDTO> detailDTOList, CartAdapter2.SelectedProduct selectedProduct) {
        this.productList = productList;
        this.detailDTOList = detailDTOList;
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
        OrderDetailDTO detailDTO = detailDTOList.get(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.cartName.setText(product.getProductName());
        holder.cartPrice.setText(formatter.format(detailDTO.getPrice()));

        String imageUrl = product.getUrlImage();
        Picasso.get().load(imageUrl).into(holder.item_giohang_image);

        holder.cartQuantity.setText(String.valueOf(detailDTO.getQuantity()));
        holder.cartTotal.setText(formatter.format(new BigDecimal(detailDTO.getQuantity()).multiply( detailDTO.getPrice()))); // Update total
        holder.bntDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateQuantity(detailDTO, 0);
                ((MyCartActivity)context).finish();
                Intent intent = new Intent((MyCartActivity)context, MyCartActivity.class);
                ((MyCartActivity)context).startActivity(intent);
            }
        });
        holder.bntTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateQuantity(detailDTO, -1);
                ((MyCartActivity)context).finish();
                Intent intent = new Intent((MyCartActivity)context, MyCartActivity.class);
                ((MyCartActivity)context).startActivity(intent);
            }
        });
        holder.bntCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateQuantity(detailDTO, 1);
                ((MyCartActivity)context).finish();
                Intent intent = new Intent((MyCartActivity)context, MyCartActivity.class);
                ((MyCartActivity)context).startActivity(intent);
            }
        });
    }

    private OrderDetailDTO UpdateQuantity(OrderDetailDTO detailDTO, int sign) {
         List<OrderDetailDTO> temp = new ArrayList<>();
         if(sign == 1){
             detailDTO.setQuantity(detailDTO.getQuantity() + 1);
         } else if(sign == 0){
             detailDTO.setQuantity(0);
         } else if(sign == -1){
             detailDTO.setQuantity(detailDTO.getQuantity() - 1);
         }

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
        if (temp.isEmpty()) {
            return null;
        }
        MyHomeActivity.loadOrderAPI(MyHomeActivity.ordersDTO.getId());
        return temp.get(0);
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