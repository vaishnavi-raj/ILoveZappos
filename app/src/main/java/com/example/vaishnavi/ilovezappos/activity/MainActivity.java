package com.example.vaishnavi.ilovezappos.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.vaishnavi.ilovezappos.ProductDetails.ProductClass;
import com.example.vaishnavi.ilovezappos.R;
import com.example.vaishnavi.ilovezappos.ZapposService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        /*SearchView is initiated to take a search input from the user.*/

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered

                return true;
            }
            /*Search query is submitted to get results.*/
            public boolean onQueryTextSubmit(final String query) {


                /*Retrofit is used for hitting the API and gettting results using a search bar*/
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.zappos.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ZapposService zapposService = retrofit.create(ZapposService.class);
                Call<ProductClass> productsCall =zapposService.getProductItemInformation(query,"b743e26728e16b81da139182bb2094357c31d331");

                productsCall.enqueue(new Callback<ProductClass>() {
                    @Override
                    public void onResponse(Call<ProductClass> call, Response<ProductClass> response) {
                        //Log.e("Response", response + "");
                        ProductClass productClass = response.body();

                        /*Getting data that has been successfully fetched using the API and stored in the Products class*/

                        String bname = productClass.getResults().get(0).getBrandName();
                        String pname = productClass.getResults().get(0).getProductName();
                        String price = productClass.getResults().get(0).getPrice();
                        String image = productClass.getResults().get(0).getThumbnailImageUrl();
                        String prodID = productClass.getResults().get(0).getProductId();
                        startIntent(query, bname, pname, price, image, prodID);
                    }
                    @Override
                    public void onFailure(Call<ProductClass> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Sorry! Try again!", Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    /*The method startIntent() is used for passing the data over to the Display activity to diplay the results in it.*/

    public void startIntent(String s, String brand_name, String product_name, String price, String image, String prodID) {
        ArrayList<String> productPage = new ArrayList<String>();
        productPage.add(0, s);
        productPage.add(1, image);
        productPage.add(2, brand_name);
        productPage.add(3, product_name);
        productPage.add(4, price);
        productPage.add(5, prodID);
        Intent intent = new Intent(this, Display.class);
        intent.putStringArrayListExtra("productDetails", productPage);
        startActivity(intent);
    }
}
