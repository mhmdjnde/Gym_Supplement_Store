package com.example.mobilesuppproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ProdDetails extends AppCompatActivity {

    private ImageButton ivBack;
    private ImageView ProdImg;
    private TextView PDesc;
    private Button BuyBut;

    // Variables to store product info from Intent
    private String PImgUrl;   // e.g., "bcaa", "whey_protein"
    private String PName;
    private String PDesc2;
    private double PPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_details);

        // Find views by ID
        ivBack = findViewById(R.id.iv_back);
        ProdImg = findViewById(R.id.iv_product_image);
        PDesc = findViewById(R.id.tv_description);
        BuyBut = findViewById(R.id.btn_buy);

        // Retrieve extras from Intent
        PName = getIntent().getStringExtra("PName");
        PImgUrl = getIntent().getStringExtra("PImg");    // e.g. "bcaa"
        PDesc2 = getIntent().getStringExtra("PDesc");
        PPrice = getIntent().getDoubleExtra("PPrice", 95.99);

        // Set the description text
        if (PName != null && PDesc2 != null) {
            PDesc.setText(PName + "\n" + PDesc2);
        }

        // Set the button text to show the price
        BuyBut.setText(BuyBut.getText().toString() + PPrice + "$");

        // Set the product image if we have a valid resource name
        if (PImgUrl != null) {
            // Convert the drawable name into a resource ID
            int resourceId = getResources().getIdentifier(PImgUrl, "drawable", getPackageName());
            if (resourceId != 0) {
                ProdImg.setImageResource(resourceId);
            } else {
                // Resource not found; optionally set a placeholder
                // ProdImg.setImageResource(R.drawable.placeholder);
            }
        }

        // Back button listener
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // or finish()
            }
        });
    }
}
