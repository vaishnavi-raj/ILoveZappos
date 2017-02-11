package com.example.vaishnavi.ilovezappos.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import com.example.vaishnavi.ilovezappos.R;
import com.example.vaishnavi.ilovezappos.databinding.ActivityResultBinding;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Vaishnavi on 07/02/2017.
 */

public class Display extends AppCompatActivity {

    /*ProductsClass for databinding*/

    public class ProductsClass {
        private final String brandName;
        private final String productName;
        private final String price;
        public ProductsClass(String brandName, String productName, String price) {
            this.brandName = brandName;
            this.productName = productName;
            this.price = price;
        }
        public String getBrandName() {
            return this.brandName;
        }
        public String getProductName() {
            return this.productName;
        }
        public String getPrice() {
            return this.price;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Declarations of VIews, ArrayLists and Animations*/

        final ArrayList<String> prodIDs = new ArrayList<String>();
        ActivityResultBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_result);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        ArrayList<String> prod = new ArrayList<String>();
        ImageView pImage = (ImageView) findViewById(R.id.productImage);
        Button button = (Button) findViewById(R.id.cart);
        final FloatingActionButton fab_cart = (FloatingActionButton) findViewById(R.id.fab);
        final Animation fabAnim, fabAnim1;
        fabAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cart_anim);
        fabAnim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_anim);
        Intent i = getIntent();
        prod = i.getStringArrayListExtra("productDetails");

        /*Using Picasso to load image into imageview*/

        Picasso.with(this).load(prod.get(1)).into(pImage);
        ProductsClass productsClass = new ProductsClass(prod.get(2), prod.get(3), prod.get(4));
        binding.setProductsClass(productsClass);

        /*Starting the animation on an item being added to the cart*/

        final ArrayList<String> finalProd = prod;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodIDs.add(0, finalProd.get(5));
                fab_cart.startAnimation(fabAnim);
            }

        });

        /*Starting the animation after something has been added and the user clicks on the shopping cart*/

        fab_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prodIDs.size() > 0) {
                    fab_cart.startAnimation(fabAnim1);
                }
            }
        });
    }
}
