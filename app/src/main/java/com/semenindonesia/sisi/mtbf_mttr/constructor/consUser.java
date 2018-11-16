package com.semenindonesia.sisi.mtbf_mttr.constructor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yosep on 11/16/2017.
 */

public class consUser {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String nama;

    @SerializedName("telp")
    private String phone;

    @SerializedName("token")
    private String token;

    public consUser(){

    }

    public consUser(String id, String nama, String phone, String token){
        this.id = id;
        this.nama = nama;
        this.phone = phone;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
