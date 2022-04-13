package com.ak.doctruyenchu.models;

import java.util.Date;


public class TRUYEN {
    private long id_truyen;
    private String ten_truyen;
    private String url_anh_nen_truyen;
    private String tac_gia;
    private String chap_moi_nhat;
    private int so_view;
    private int de_cu;
    private String mo_ta;
    private Date ngay_dang;
    private float rate;
    private int so_luot_rate;

    public TRUYEN() {
    }

    public TRUYEN(String Url) {
        this.url_anh_nen_truyen = Url;
    }


    public void themTruyen(String ten_truyen,String mo_ta,String url_anh_nen_truyen){
        this.id_truyen = (new Date()).getTime();
        this.ten_truyen = ten_truyen;
        this.url_anh_nen_truyen = url_anh_nen_truyen;
        this.tac_gia = "none";
        this.chap_moi_nhat = "none";
        this.ngay_dang = new Date();
        this.so_view = 0;
        this.de_cu=0;
        this.mo_ta=mo_ta;
        this.rate =0;
        this.so_luot_rate = 0;
    }



    public TRUYEN(String ten_truyen, String url_anh_nen_truyen, String tac_gia, String chap_moi_nhat, int so_view, String mo_ta) {
        this.ten_truyen = ten_truyen;
        this.url_anh_nen_truyen = url_anh_nen_truyen;
        this.tac_gia = tac_gia;
        this.chap_moi_nhat = chap_moi_nhat;
        this.so_view = so_view;
        this.mo_ta = mo_ta;
        this.rate = 0.0f;
        this.so_luot_rate = 0;
    }

    public TRUYEN( String ten_truyen, String url_anh_nen_truyen, String tac_gia, String chap_moi_nhat, int so_view,int de_cu,String mo_ta) {
        this.id_truyen = (new Date()).getTime();
        this.ten_truyen = ten_truyen;
        this.url_anh_nen_truyen = url_anh_nen_truyen;
        this.tac_gia = tac_gia;
        this.chap_moi_nhat = chap_moi_nhat;
        this.ngay_dang = new Date();
        this.so_view = so_view;
        this.de_cu=de_cu;
        this.mo_ta=mo_ta;
    }

    public long getId_truyen() {
        return id_truyen;
    }

    public void setId_truyen(long id_truyen) {
        this.id_truyen = id_truyen;
    }

    public String getTen_truyen() {
        return ten_truyen;
    }

    public void setTen_truyen(String ten_truyen) {
        this.ten_truyen = ten_truyen;
    }

    public String getUrl_anh_nen_truyen() {
        return url_anh_nen_truyen;
    }

    public void setUrl_anh_nen_truyen(String url_anh_nen_truyen) {
        this.url_anh_nen_truyen = url_anh_nen_truyen;
    }

    public String getTac_gia() {
        return tac_gia;
    }

    public void setTac_gia(String tac_gia) {
        this.tac_gia = tac_gia;
    }

    public String getChap_moi_nhat() {
        return chap_moi_nhat;
    }

    public void setChap_moi_nhat(String chap_moi_nhat) {
        this.chap_moi_nhat = chap_moi_nhat;
    }

    public int getSo_view() {
        return so_view;
    }

    public void setSo_view(int so_view) {
        this.so_view = so_view;
    }

    public int getDe_cu() {
        return de_cu;
    }

    public void setDe_cu(int de_cu) {
        this.de_cu = de_cu;
    }

    public Date getNgay_dang() {
        return ngay_dang;
    }

    public void setNgay_dang(Date ngay_dang) {
        this.ngay_dang = ngay_dang;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getSo_luot_rate() {
        return so_luot_rate;
    }

    public void setSo_luot_rate(int so_luot_rate) {
        this.so_luot_rate = so_luot_rate;
    }
}
