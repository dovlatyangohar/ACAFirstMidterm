package com.example.exam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductModel> products;
    private OnItemClickListener itemClickListener;


    public ProductAdapter(List<ProductModel> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_item, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final ProductModel product = products.get(position);

        holder.name.setText(product.getProductName());
        holder.price.setText(product.getProductPrice() + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClickListener(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.productNameTextView);
            price = itemView.findViewById(R.id.productPriceTextView);
        }
    }

    interface OnItemClickListener {
        void onItemClickListener(ProductModel product);

    }
}
