package com.example.prm392myquizapplication.data;


import java.io.Serializable;

public class StudySet implements Serializable {
    private int idBo;
    private int stt;
    private String TenBo;

    public StudySet() {

    }
    public StudySet(int idBo, int stt, String tenBo) {
        this.idBo = idBo;
        this.stt = stt;
        TenBo = tenBo;
    }

    public int getIdBo() {
        return idBo;
    }

    public void setIdBo(int idBo) {
        this.idBo = idBo;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getTenBo() {
        return TenBo;
    }

    public void setTenBo(String tenBo) {
        TenBo = tenBo;
    }
}
