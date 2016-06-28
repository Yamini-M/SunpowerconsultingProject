package com.yaminitask.api;

import com.yaminitask.model.AllCarMakes;
import com.yaminitask.model.StockResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by manasal on 28/06/16.
 */
public interface ApiEndpointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("api/inventory/v2/franchises/")
    Call<StockResponse> getStockResponseCall(@Field("franchiseId") String franchiseId, @Field("pageSize") String pageSize);

    @GET("api/vehicle/v2/makes")
    Call<AllCarMakes> getMakesCall(@Query("state") String state, @Query("year") String year, @Query("api_key") String apiKey);

    @GET("api/vehicle/v2/makes")
    Call<AllCarMakes> getMakesCall(@Query("api_key") String apiKey);
}
