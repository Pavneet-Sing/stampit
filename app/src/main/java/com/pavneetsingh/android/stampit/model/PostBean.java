package com.pavneetsingh.android.stampit.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.IgnoreExtraProperties;
import com.pavneetsingh.android.stampit.room.PostDAO;


/**
 * Created by Pavneet Singh on 03/05/18.
 * Contact : dev.pavneet@gmail.com
 */

@IgnoreExtraProperties
@Entity(tableName = PostDAO.TABLE)
public class PostBean {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String userId;
    private String pushKey;
    private String name;
    private String desc;
    private String url;
    private String loc;
    private double cost;

    @Override
    public String toString() {
        return "PostBean{" +
                "id=" + id +
                ", pushKey='" + pushKey + '\'' +
                ", name='" + name + '\'' +
                ", user='" + userId + '\'' +
                ", desc='" + desc + '\'' +
                ", url='" + url + '\'' +
                ", loc='" + loc + '\'' +
                ", cost=" + cost +
                '}';
    }

    public PostBean() {
    }

    @Ignore
    public PostBean( String name, String desc, String url, String loc, double cost) {
        this.name = name;
        this.desc = desc;
        this.url = url;
        this.loc = loc;
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostBean postBean = (PostBean) o;

        if (id != postBean.id) return false;
        return name != null ? name.equals(postBean.name) : postBean.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int)(id & 0xFFFFFFFF);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getLoc() {return loc;}

    public void setLoc(String loc) {this.loc = loc;}

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
