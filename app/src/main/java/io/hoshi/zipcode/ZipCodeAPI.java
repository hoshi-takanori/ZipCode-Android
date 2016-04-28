package io.hoshi.zipcode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hoshi on 2016/04/28.
 */
public interface ZipCodeAPI {
    String API_URL = "http://search.olp.yahooapis.jp";
    String OUTPUT_JSON = "json";
    String API_KEY = "YOUR_API_KEY";

    @GET("/OpenLocalPlatform/V1/zipCodeSearch")
    Call<ZipCodeResult> zipCodeSearch(@Query("query") String query, @Query("output") String output, @Query("appid") String appid);
}
