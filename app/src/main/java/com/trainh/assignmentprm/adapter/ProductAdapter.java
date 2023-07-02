package com.trainh.assignmentprm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trainh.assignmentprm.R;
import com.trainh.assignmentprm.entities.Product;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterVh> implements Filterable {

    private List<Product> productList;
    private List<Product> getProductListFiltered;
    private Context context;
    public SelectedProduct selectedProduct;

    public ProductAdapter(List<Product> productList, SelectedProduct selectedProduct) {
        this.productList = productList;
        this.getProductListFiltered = productList;
        this.selectedProduct = selectedProduct;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ProductAdapterVh(LayoutInflater.from(context).inflate(R.layout.row_product,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductAdapterVh holder, int position) {

        Product product = productList.get(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        String username = product.getName();
        String price = formatter.format(product.getPrice()) + " vnd";
        int image = product.getImage();
        holder.tvName.setText(username);
        holder.tvPrice.setText(price);
        holder.ivProduct.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence == null | charSequence.length() == 0){
                    filterResults.count = getProductListFiltered.size();
                    filterResults.values = getProductListFiltered;

                }else{
                    String searchChr = charSequence.toString().toLowerCase();

                    List<Product> resultData = new ArrayList<>();

                    for(Product userModel: getProductListFiltered){
                        if(userModel.getName().toLowerCase().contains(searchChr)){
                            resultData.add(userModel);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                productList = (List<Product>) filterResults.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }


    public interface SelectedProduct{

        void selectedProduct(Product product);

    }

    public class ProductAdapterVh extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvPrice;
        ImageView ivProduct;
        ImageView imIcon;
        public ProductAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedProduct.selectedProduct(productList.get(getAdapterPosition()));
                }
            });
        }
    }
}
