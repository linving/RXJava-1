package com.example.app.rxjava.bean.picture;

import com.example.app.rxjava.base.db.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Administrator on 2016/3/10.
 */
@ModelContainer
@Table(database = AppDatabase.class)
public class Data extends BaseModel {
    @PrimaryKey(autoincrement = false)
    private long id;
    private int setId;
    private int pn;
    @Column
    private String thumbnail_url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }
}
