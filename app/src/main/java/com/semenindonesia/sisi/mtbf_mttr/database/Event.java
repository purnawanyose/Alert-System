package com.semenindonesia.sisi.mtbf_mttr.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yosep on 11/20/2017.
 */

public class Event extends RealmObject{

    @PrimaryKey
    private int id;

    private String idmesin, mesin;
    private String opco;
    private String status;
    private String idevent;
    private String datecreated, dateupdated, dateupdateat;
    private Integer isRead, tampil;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdmesin() {
        return idmesin;
    }

    public void setIdmesin(String idmesin) {
        this.idmesin = idmesin;
    }

    public String getMesin() {
        return mesin;
    }

    public void setMesin(String mesin) {
        this.mesin = mesin;
    }

    public String getOpco() {
        return opco;
    }

    public void setOpco(String opco) {
        this.opco = opco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdevent() {
        return idevent;
    }

    public void setIdevent(String idevent) {
        this.idevent = idevent;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(String dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getDateupdateat() {
        return dateupdateat;
    }

    public void setDateupdateat(String dateupdateat) {
        this.dateupdateat = dateupdateat;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getTampil() {
        return tampil;
    }

    public void setTampil(Integer tampil) {
        this.tampil = tampil;
    }
}
