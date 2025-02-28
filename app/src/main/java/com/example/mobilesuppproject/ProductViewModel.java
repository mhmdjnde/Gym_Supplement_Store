package com.example.mobilesuppproject;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<String>> allCategories;

    public ProductViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProducts();
        allCategories = productDao.getAllCategories();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    // Get all categories
    public LiveData<List<String>> getAllCategories() {
        return allCategories;
    }

    // Get products by category
    public LiveData<List<Product>> getProductsByCategory(String category) {
        return productDao.getProductsByCategory(category);
    }

    // Insert a new product (generic method if you need it)
    public void insert(Product product) {
        new Thread(() -> {
            productDao.insert(product);
        }).start();
    }



    public LiveData<List<Product>> searchProducts(String query) {
        return productDao.searchProductsByName(query);
    }


    // ----------------------------------------------------------------
    // Method to insert sample data IF each product doesn't already exist
    // ----------------------------------------------------------------
    public void insertSampleData() {
        new Thread(() -> {

            // Supplements
            if (productDao.getCountByName("Whey Protein") == 0) {
                productDao.insert(new Product(
                        "Whey Protein",
                        "Supplements",
                        49.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.whey_protein,
                        "Premium whey protein powder"
                ));
            }

            if (productDao.getCountByName("BCAA") == 0) {
                productDao.insert(new Product(
                        "BCAA",
                        "Supplements",
                        29.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.bcaa,
                        "Branched Chain Amino Acids"
                ));
            }

            if (productDao.getCountByName("Creatine") == 0) {
                productDao.insert(new Product(
                        "Creatine",
                        "Supplements",
                        19.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.creatine,
                        "Pure Creatine Monohydrate"
                ));
            }

            if (productDao.getCountByName("Amino Acid") == 0) {
                productDao.insert(new Product(
                        "Amino Acid",
                        "Supplements",
                        64.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.aminoacid,
                        "Pure Amino Acid"
                ));
            }

            if (productDao.getCountByName("Glutamine") == 0) {
                productDao.insert(new Product(
                        "Glutamine",
                        "Supplements",
                        25.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.glutamine,
                        "Pure Glutamine"
                ));
            }

            if (productDao.getCountByName("MultiVitamin") == 0) {
                productDao.insert(new Product(
                        "MultiVitamin",
                        "Supplements",
                        54.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.multivitamin,
                        "Pure MultiVitamin"
                ));
            }

            if (productDao.getCountByName("Omega 3") == 0) {
                productDao.insert(new Product(
                        "Omega 3",
                        "Supplements",
                        19.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.omega3,
                        "Pure Omega 3"
                ));
            }

            if (productDao.getCountByName("Ashwagandha") == 0) {
                productDao.insert(new Product(
                        "Ashwagandha",
                        "Supplements",
                        28.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.ashvaganda,
                        "Pure Ashwagandha"
                ));
            }

            // T-Shirts
            if (productDao.getCountByName("Muscle Fit Tee") == 0) {
                productDao.insert(new Product(
                        "Muscle Fit Tee",
                        "T-Shirts",
                        29.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.muscle_tee,
                        "Form-fitting workout shirt"
                ));
            }

            if (productDao.getCountByName("GymTier T-Shirt") == 0) {
                productDao.insert(new Product(
                        "GymTier T-Shirt",
                        "T-Shirts",
                        99.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.gymtier,
                        "Half-sleeve sweater"
                ));
            }

            if (productDao.getCountByName("SixPackSoon funny T-Shirt") == 0) {
                productDao.insert(new Product(
                        "SixPackSoon funny T-Shirt",
                        "T-Shirts",
                        57.66,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.sizpacksoon,
                        "funny Short-sleeve pullover"
                ));
            }

            if (productDao.getCountByName("Short-sleeve sweatshirt green") == 0) {
                productDao.insert(new Product(
                        "Short-sleeve sweatshirt green",
                        "T-Shirts",
                        67.15,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.stikman,
                        "white Short-sleeve pullover, gentle"
                ));
            }

            if (productDao.getCountByName("Short-sleeve sweatshirt sunny green") == 0) {
                productDao.insert(new Product(
                        "Short-sleeve sweatshirt sunny green",
                        "T-Shirts",
                        37.66,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.wear,
                        "white Short-sleeve pullover, gentle"
                ));
            }

            // Equipment
            if (productDao.getCountByName("Dumbbells Set") == 0) {
                productDao.insert(new Product(
                        "Dumbbells Set",
                        "Equipment",
                        99.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.dumbbells,
                        "10kg dumbbells set"
                ));
            }

            if (productDao.getCountByName("Resistance Bands") == 0) {
                productDao.insert(new Product(
                        "Resistance Bands",
                        "Equipment",
                        19.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.resistance_bands,
                        "Set of 5 resistance bands"
                ));
            }

            if (productDao.getCountByName("human hand grip") == 0) {
                productDao.insert(new Product(
                        "human hand grip",
                        "Equipment",
                        19.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.handgrip,
                        "2 hand grips strong with degrees"
                ));
            }

            if (productDao.getCountByName("PullUp Bar for home") == 0) {
                productDao.insert(new Product(
                        "PullUp Bar for home",
                        "Equipment",
                        19.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.pullup,
                        "PullUp bar that can be used at home to workout."
                ));
            }

            if (productDao.getCountByName("PushUp Bar for home") == 0) {
                productDao.insert(new Product(
                        "PushUp Bar for home",
                        "Equipment",
                        19.99,
                        "android.resource://" + getApplication().getPackageName() + "/" + R.drawable.pushuptools,
                        "PullUp bar that can be used at home to workout."
                ));
            }

            Log.d("ProductViewModel", "Sample data inserted (with checks).");
        }).start();
    }
}
