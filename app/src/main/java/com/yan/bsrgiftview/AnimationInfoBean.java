package com.yan.bsrgiftview;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2016/12/15.
 */
public class AnimationInfoBean implements Serializable {

    private int code;
    private List<List<DataBean>> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        private int id;
        private String name;
        private String icon;
        private int price;

        public DataBean(int id, String name, String icon, int price) {
            this.id = id;
            this.name = name;
            this.icon = icon;
            this.price = price;
        }

        public DataBean(int id, String name, String icon) {
            this(id, name, icon,0);
        }

        public DataBean() {
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
