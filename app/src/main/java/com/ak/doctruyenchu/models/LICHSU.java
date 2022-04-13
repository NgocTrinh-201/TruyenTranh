package com.ak.doctruyenchu.models;

import java.util.Date;

public class LICHSU{

    private String ten_truyen;
    private String ten_chuong;
    private long time;

    public LICHSU() {
    }

    public LICHSU(String ten_truyen, String ten_chuong) {
        this.ten_truyen = ten_truyen;
        this.ten_chuong = ten_chuong;
        this.time = new Date().getTime();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTen_truyen() {
        return ten_truyen;
    }

    public void setTen_truyen(String ten_truyen) {
        this.ten_truyen = ten_truyen;
    }

    public String getTen_chuong() {
        return ten_chuong;
    }

    public void setTen_chuong(String ten_chuong) {
        this.ten_chuong = ten_chuong;
    }
}
