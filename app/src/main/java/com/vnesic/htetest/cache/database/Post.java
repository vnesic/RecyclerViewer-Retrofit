package com.vnesic.htetest.cache.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Post {
    @ColumnInfo(name = "userId")
    private int mUserId;

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "body")
    private String mBody;

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }

    public int getUserId() {
        return mUserId;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    @Override
    public String toString() {
        return "Post{" +
                ", mUserId=" + mUserId +
                ", mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mBody='" + mBody + '\'' +
                '}';
    }
}
