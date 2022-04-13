package com.ak.doctruyenchu.ui.moduleView;


import com.ak.doctruyenchu.Constans.Constans;

public class Module {
    public boolean orientationVertical;
    public int rows;
    public String moduleName;
    public int backgroungColor = 0;
    public int titleColor = 0;
    public boolean enableOptionButton;

    public Module(String moduleName, boolean enableOptionButton ) {
        this.orientationVertical = false ;
        this.rows = 2;
        this.moduleName = moduleName;
        this.backgroungColor = 0;
        this.titleColor = 0;
        this.enableOptionButton = enableOptionButton;
    }

    public Module(String moduleName, int rows,boolean orientationVertical ) {
        this.orientationVertical = orientationVertical;
        this.rows = rows;
        this.moduleName = moduleName;
        this.backgroungColor = 0;
        this.titleColor = 0;
        this.enableOptionButton = false;
    }

    public Module(String moduleName, int rows,boolean orientationVertical,int backgroungColor) {
        this.orientationVertical = orientationVertical;
        this.rows = rows;
        this.moduleName = moduleName;
        this.backgroungColor = backgroungColor;
        this.titleColor = 2131034733;
        this.enableOptionButton = false;
    }

    public Module(String moduleName, int rows,boolean orientationVertical,int backgroungColor, int titleColor) {
        this.orientationVertical = orientationVertical;
        this.rows = rows;
        this.moduleName = moduleName;
        this.backgroungColor = backgroungColor;
        this.titleColor = titleColor;
        this.enableOptionButton = false;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public boolean isEnableOptionButton() {
        return enableOptionButton;
    }

    public void enableOptionButton() {
        this.enableOptionButton = true;
    }

    public int getBackgroungColor() {
        return backgroungColor;
    }

    public void setBackgroungColor(int backgroungColor) {
        this.backgroungColor = backgroungColor;
    }

    public boolean isOrientationVertical() {
        return orientationVertical;
    }

    public void setOrientationVertical(boolean orientationVertical) {
        this.orientationVertical = orientationVertical;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
