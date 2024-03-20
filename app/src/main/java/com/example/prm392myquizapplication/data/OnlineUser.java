package com.example.prm392myquizapplication.data;

public class OnlineUser {
    private String iduser;
    private String HoTen;
    private String Email;
    private String SDT;

    public OnlineUser() {
        //Nhận data từ Firebase
    }

    public OnlineUser(String iduser, String hoTen, String email, String SDT) {
        this.iduser = iduser;
        HoTen = hoTen;
        Email = email;
        this.SDT = SDT;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

}
