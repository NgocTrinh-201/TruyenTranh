package com.ak.doctruyenchu.models;

import java.util.Date;

public class DanhGia {
    private float rate;
    private long ngay_danh_gia;
    private String noi_dung;


    public DanhGia() {
    }

    public DanhGia(float rate) {
        this.rate = rate;
        this.ngay_danh_gia = new Date().getTime();
        this.noi_dung="none";
    }

    public DanhGia(float rate, String noi_dung) {
        this.rate = rate;
        this.noi_dung = noi_dung;
        this.ngay_danh_gia = new Date().getTime();
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getNgay_danh_gia() {
        return ngay_danh_gia;
    }

    public void setNgay_danh_gia(long ngay_danh_gia) {
        this.ngay_danh_gia = ngay_danh_gia;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }
}
