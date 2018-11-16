package com.semenindonesia.sisi.mtbf_mttr.constructor;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yosep on 11/20/2017.
 */

public class consEvent {

    private String workstation, mk, timestamp, opco, status,idevent, idmesin;
    private Integer id, isRead, sts_tampil;
    private String update, update_at;

    public consEvent(){

    }

    public consEvent(String workstation, String mk, String timestamp, String opco, String status,
                     String idevent, String idmesin, Integer id, Integer isRead, Integer sts_tampil,
                     String update, String update_at) {
        this.workstation = workstation;
        this.mk = mk;
        this.timestamp = timestamp;
        this.opco = opco;
        this.status = status;
        this.idevent = idevent;
        this.idmesin = idmesin;
        this.id = id;
        this.isRead = isRead;
        this.sts_tampil = sts_tampil;
        this.update = update;
        this.update_at = update_at;
    }

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public String getIdmesin() {
        return idmesin;
    }

    public void setIdmesin(String idmesin) {
        this.idmesin = idmesin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getSts_tampil() {
        return sts_tampil;
    }

    public void setSts_tampil(Integer sts_tampil) {
        this.sts_tampil = sts_tampil;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
