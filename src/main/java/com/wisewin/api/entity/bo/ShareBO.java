package com.wisewin.api.entity.bo;

public class ShareBO {
    private Integer id;
    private String value;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "ShareBO{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
