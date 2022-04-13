package com.ak.doctruyenchu.models;

import java.util.ArrayList;

public class TEST {
    private String a;
    private String b;
    private ArrayList<String> arrayList;

    public TEST(String a, String b, ArrayList<String> arrayList) {
        this.a = a;
        this.b = b;
        this.arrayList = arrayList;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
