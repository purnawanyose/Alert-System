package com.semenindonesia.sisi.mtbf_mttr.retrofit;

import com.google.gson.annotations.SerializedName;
import com.semenindonesia.sisi.mtbf_mttr.constructor.consUser;

/**
 * Created by yosep on 11/16/2017.
 */

public class resultLogin {

    @SerializedName("error")
    private boolean error;

    @SerializedName("error_msg")
    private String error_msg;

    @SerializedName("user")
    private consUser user;

    public resultLogin(Boolean error, String error_msg, consUser user){
        this.error = error;
        this.error_msg = error_msg;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public consUser getUser() {
        return user;
    }
}
