package com.trainh.assignmentprm.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.activity.my_interface.IClickProductListener;
import com.trainh.assignmentprm.adapter.ProductAdapter;
import com.trainh.assignmentprm.entities.Product;
import com.trainh.assignmentprm.model.ProductDTO;

import java.util.List;

public class ProductAdapter2 extends RecyclerView.Adapter<ProductAdapter2.ProductViewHolder>{

    private List<ProductDTO> productList;
    private IClickProductListener productListener;

    public ProductAdapter2(List<ProductDTO> productList, IClickProductListener productListener) {
        this.productList = productList;
        this.productListener = productListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductDTO dto = productList.get(position);
        if(dto == null){
            return;
        }
        String imageUrl = dto.getUrlImage();
        Picasso.get().load(imageUrl).into(holder.ivProduct);
        holder.tvName.setText(dto.getProductName());
        holder.tvPrice.setText(String.valueOf(dto.getUnitPrice()));
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListener.onClickItemProduct(dto);
            }
        });
    }

    @Override
    public int getItemCount() {
         if(productList != null){
             return productList.size();
         };
         return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
    // Khai bao thành phần có trong layout item

        ConstraintLayout layoutItem;
        private ImageView ivProduct;
        private TextView tvName;
        private TextView tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productListener.onClickItemProduct(productList.get(getAdapterPosition()));
                }
            });
        }
    }
}
