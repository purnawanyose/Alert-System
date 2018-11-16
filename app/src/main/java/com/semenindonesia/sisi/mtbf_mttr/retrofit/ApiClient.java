package com.semenindonesia.sisi.mtbf_mttr.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yosep on 11/16/2017.
 */

public class ApiClient {

    public static final String BASE_URL = "http://10.15.5.150/dev/mso/masterapi/update_token/";

    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
