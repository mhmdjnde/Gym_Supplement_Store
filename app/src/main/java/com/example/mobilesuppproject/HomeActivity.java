package com.example.mobilesuppproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.lifecycle.ViewModelProvider;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ProductViewModel productViewModel;
    private CategoryPagerAdapter pagerAdapter;
    private BottomNavigationView bottomNavigationView;
    String uemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        uemail = getIntent().getStringExtra("userEmail").toString();

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.insertSampleData();

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup ViewPager with our adapter
        pagerAdapter = new CategoryPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("All");
                            break;
                        case 1:
                            tab.setText("Supplements");
                            break;
                        case 2:
                            tab.setText("T-Shirts");
                            break;
                        case 3:
                            tab.setText("Equipment");
                            break;
                    }
                }
        ).attach();

        // Bottom navigation setup
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                // Already on home
                return true;
            } else if (id == R.id.nav_cart) {
                return true;
            } else if (id == R.id.nav_settings) {
                Intent i = new Intent(HomeActivity.this, EditProfActivity.class);
                i.putExtra("userEmail", uemail);
                startActivityForResult(i, 1000);
                return true;
            }
            return false;
        });
    }

    private void doProductSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            // Possibly restore full list or do nothing
            return;
        }

        productViewModel.searchProducts(query).observe(this, productList -> {
            AllProductsFragment allFragment = pagerAdapter.getAllProductsFragment();
            allFragment.UpdateList(productList);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.widget.SearchView searchView = (android.widget.SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search by product name...");

        // Listen for text changes
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doProductSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doProductSearch(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_profile) {
            Intent i = new Intent(HomeActivity.this, EditProfActivity.class);
            i.putExtra("userEmail", uemail);
            startActivityForResult(i, 1000);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            if (data != null) {
                 uemail = data.getStringExtra("updatedName");
            }
        }
    }
}
