package com.example.mobilesuppproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CategoryPagerAdapter extends FragmentStateAdapter {

    private AllProductsFragment allProductsFragment;
    private SupplementsFragment supplementsFragment;
    private TShirtsFragment tShirtsFragment;
    private EquipmentFragment equipmentFragment;

    public CategoryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        // Create them once and hold onto the references
        allProductsFragment = new AllProductsFragment();
        supplementsFragment = new SupplementsFragment();
        tShirtsFragment = new TShirtsFragment();
        equipmentFragment = new EquipmentFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return allProductsFragment;
            case 1:
                return supplementsFragment;
            case 2:
                return tShirtsFragment;
            case 3:
                return equipmentFragment;
            default:
                return allProductsFragment; // fallback
        }
    }

    @Override
    public int getItemCount() {
        return 4; // total number of tabs
    }

    // Expose getter for AllProductsFragment
    public AllProductsFragment getAllProductsFragment() {
        return allProductsFragment;
    }
}
