package com.hku.msc.menu;

public class MenuModel {
    public String menuName;
    public boolean hasChildren, isGroup;

    private String url;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren) {
        this.menuName = menuName;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
