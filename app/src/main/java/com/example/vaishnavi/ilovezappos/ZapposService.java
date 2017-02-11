package com.example.vaishnavi.ilovezappos;

/**
 * Created by Vaishnavi on 07/02/2017.
 */

import com.example.vaishnavi.ilovezappos.ProductDetails.ProductClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ZapposService {
    /**
     *
     * @param "term"
     * @param "key"
     * @return
     */

    @GET("/Search")
    Call<ProductClass> getProductItemInformation(@Query("term") String paramterm,
                                                 @Query("key") String paramKey);
}

