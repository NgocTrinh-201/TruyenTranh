package com.ak.doctruyenchu.models;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CHUONG {
    private long id_chuong;
    private String ten_chuoong;
    private String ngay_cap_nhat;
    private String noi_dung;

    public CHUONG( String ten_chuoong, String noi_dung) {
        this.id_chuong = new Date().getTime();
        this.ten_chuoong = ten_chuoong;
        this.ngay_cap_nhat = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
        this.noi_dung = noi_dung;
    }

    public CHUONG() {
    }



    public long getId_chuong() {
        return id_chuong;
    }

    public void setId_chuong(long id_chuong) {
        this.id_chuong = id_chuong;
    }

    public String getTen_chuoong() {
        return ten_chuoong;
    }

    public void setTen_chuoong(String ten_chuoong) {
        this.ten_chuoong = ten_chuoong;
    }

    public String getNgay_cap_nhat() {
        return ngay_cap_nhat;
    }

    public void setNgay_cap_nhat(String ngay_cap_nhat) {
        this.ngay_cap_nhat = ngay_cap_nhat;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }
}
