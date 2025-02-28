package com.example.mobilesuppproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EquipmentFragment extends Fragment {

    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_equipment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        // You'll set the adapter here later



        ProductAdapter adapter = new ProductAdapter(new ArrayList<>(),
                product -> {
                    // Handle product click
                    Intent i = new Intent(getActivity(), ProdDetails.class);
                    i.putExtra("PName", product.getName());
                    i.putExtra("PPrice", product.getPrice());
                    i.putExtra("PImage", product.getImageUrl());
                    i.putExtra("PDesc", product.getDescription());
                    startActivity((i));                });
        recyclerView.setAdapter(adapter);

        // Get ViewModel and observe products
        ProductViewModel viewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        viewModel.getProductsByCategory("Equipment").observe(getViewLifecycleOwner(),
                products -> {
                    Log.d("EquipmentFragment", "Products loaded: " + products.size());
                    adapter.setProducts(products);
                });
        return view;

    }
}
