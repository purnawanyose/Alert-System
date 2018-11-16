package com.semenindonesia.sisi.mtbf_mttr.retrofit;

import android.renderscript.Sampler;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yosep on 11/16/2017.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<resultLogin> login (@Field("mobile") String mobile,
                               @Field("token") String token);
    @FormUrlEncoded
    @POST("sms.php")
    Call<resultLogin> verify (@Field("mobile") String mobile,
                             @Field("otp") String otp,
                              @Field("token") String token);

}
