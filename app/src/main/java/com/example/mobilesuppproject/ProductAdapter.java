package com.example.mobilesuppproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<Product> products;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }


    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }


    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }


    public int getItemCount() {
        return products != null ? products.size() : 0;
    }


    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView priceView;

        ProductViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image);
            nameView = itemView.findViewById(R.id.product_name);
            priceView = itemView.findViewById(R.id.product_price);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onProductClick(products.get(position));
                }
            });
        }

        void bind(Product product) {
            nameView.setText(product.getName());
            priceView.setText(String.format("$%.2f", product.getPrice()));

            // Log for debugging
            Log.d("ProductAdapter", "Loading image: " + product.getImageUrl());

            try {
                Glide.with(itemView.getContext())
                        .load(product.getImageUrl())
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                        .into(imageView);
            } catch (Exception e) {
                Log.e("ProductAdapter", "Error loading image: " + e.getMessage());
                // Fallback to placeholder if loading fails
                imageView.setImageResource(R.drawable.placeholder_image);
            }
        }
    }
}
