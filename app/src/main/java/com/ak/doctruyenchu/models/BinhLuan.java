package com.ak.doctruyenchu.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BinhLuan {
    private String ten_nguoi_dung;
    private String uid;
    private String url_anh_dai_dien;
    private String nghay_dang;
    private String noi_dung;

    public BinhLuan() {
    }


    public BinhLuan(String ten_nguoi_dung, String uid, String url_anh_dai_dien, String noi_dung) {
        this.ten_nguoi_dung = ten_nguoi_dung;
        this.uid = uid;
        this.url_anh_dai_dien = url_anh_dai_dien;
        this.noi_dung = noi_dung;
        this.nghay_dang = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
    }

    public String getTen_nguoi_dung() {
        return ten_nguoi_dung;
    }

    public void setTen_nguoi_dung(String ten_nguoi_dung) {
        this.ten_nguoi_dung = ten_nguoi_dung;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl_anh_dai_dien() {
        return url_anh_dai_dien;
    }

    public void setUrl_anh_dai_dien(String url_anh_dai_dien) {
        this.url_anh_dai_dien = url_anh_dai_dien;
    }

    public String getNghay_dang() {
        return nghay_dang;
    }

    public void setNghay_dang(String nghay_dang) {
        this.nghay_dang = nghay_dang;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }
}
