package com.example.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SignBean {
    @Id(autoincrement = true)
    private Long _id;
    private String date;
    private String time;
    private String type;

    public SignBean( String date, String type) {

        this.date = date;
        this.type = type;
    }

    public SignBean( String date, String time,String type) {

        this.time = time;
        this.date = date;
        this.type = type;
    }


    @Generated(hash = 1440706430)
    public SignBean() {
    }
    @Generated(hash = 1692205691)
    public SignBean(Long _id, String date, String time, String type) {
        this._id = _id;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    @Override
    public String toString() {
        return "SignBean{" +
                "_id=" + _id +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
