package com.example.mobilesuppproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AllProductsFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private ProductViewModel viewModel;
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all_products, container, false);

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Observe categories and create sections
        viewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            LinearLayout container2 = rootView.findViewById(R.id.categories_container);
            container2.removeAllViews();

            for (String category : categories) {
                View categoryView = createCategorySection(category);
                container2.addView(categoryView);
            }
        });

        return rootView;
    }

    public void UpdateList(List<Product> newList) {
        // 1) Group products by category
        Map<String, List<Product>> categoryMap = new LinkedHashMap<>();
        for (Product p : newList) {
            String category = p.getCategory();
            if (!categoryMap.containsKey(category)) {
                categoryMap.put(category, new ArrayList<>());
            }
            categoryMap.get(category).add(p);
        }

        // 2) Clear out the old views
        LinearLayout container2 = rootView.findViewById(R.id.categories_container);
        container2.removeAllViews();

        // 3) For each category, create a new horizontal section
        for (String category : categoryMap.keySet()) {
            View categoryView = getLayoutInflater().inflate(R.layout.layout_category_section, null);
            TextView titleView = categoryView.findViewById(R.id.category_title);
            RecyclerView recyclerView = categoryView.findViewById(R.id.products_recycler_view);

            titleView.setText(category);

            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            // New adapter for this category
            ProductAdapter adapter = new ProductAdapter(new ArrayList<>(), AllProductsFragment.this);
            recyclerView.setAdapter(adapter);

            // Supply the products for this category
            adapter.setProducts(categoryMap.get(category));

            // Add this category block to the main container
            container2.addView(categoryView);
        }
    }

    private View createCategorySection(String category) {
        View view = getLayoutInflater().inflate(R.layout.layout_category_section, null);
        TextView titleView = view.findViewById(R.id.category_title);
        RecyclerView recyclerView = view.findViewById(R.id.products_recycler_view);

        titleView.setText(category);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ProductAdapter adapter = new ProductAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        viewModel.getProductsByCategory(category).observe(getViewLifecycleOwner(),
                products -> adapter.setProducts(products));

        return view;
    }

    public void onProductClick(Product product)
    {
        Intent i = new Intent(getActivity(), ProdDetails.class);
        i.putExtra("PName", product.getName());
        i.putExtra("PPrice", product.getPrice());
        i.putExtra("PImage", product.getImageUrl());
        i.putExtra("PDesc", product.getDescription());
        startActivity((i));
    }



}
