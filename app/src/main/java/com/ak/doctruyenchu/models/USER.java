package com.ak.doctruyenchu.models;

import com.ak.doctruyenchu.Constans.Constans;

public class USER {
    private String uid;
    private String ten_nguoi_dung;
    private String url_anh_dai_dien;
    private String email;
    private String loai_tai_khoan;

    public USER() {
    }

    public USER(String uid, String ten_nguoi_dung, String url_anh_dai_dien, String email) {
        this.uid = uid;
        this.ten_nguoi_dung = ten_nguoi_dung;
        this.url_anh_dai_dien = url_anh_dai_dien;
        this.email = email;
        this.loai_tai_khoan = Constans.READER;
    }

    public USER(String uid, String ten_nguoi_dung, String url_anh_dai_dien, String email,String loai_tai_khoan) {
        this.uid = uid;
        this.ten_nguoi_dung = ten_nguoi_dung;
        this.url_anh_dai_dien = url_anh_dai_dien;
        this.email = email;
        this.loai_tai_khoan = loai_tai_khoan;
    }

    public String getLoai_tai_khoan() {
        return loai_tai_khoan;
    }

    public void setLoai_tai_khoan(String loai_tai_khoan) {
        this.loai_tai_khoan = loai_tai_khoan;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTen_nguoi_dung() {
        return ten_nguoi_dung;
    }

    public void setTen_nguoi_dung(String ten_nguoi_dung) {
        this.ten_nguoi_dung = ten_nguoi_dung;
    }

    public String getUrl_anh_dai_dien() {
        return url_anh_dai_dien;
    }

    public void setUrl_anh_dai_dien(String url_anh_dai_dien) {
        this.url_anh_dai_dien = url_anh_dai_dien;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
