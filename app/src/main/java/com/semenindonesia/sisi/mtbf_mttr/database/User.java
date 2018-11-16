package com.semenindonesia.sisi.mtbf_mttr.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yosep on 11/16/2017.
 */

public class User extends RealmObject {

    @PrimaryKey
    private int id;

    private String name;
    private String phone;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
