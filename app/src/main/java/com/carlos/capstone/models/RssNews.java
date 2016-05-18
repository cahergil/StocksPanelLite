package com.carlos.capstone.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Carlos on 20/01/2016.
 */
public class RssNews implements Parcelable {
    private String title;
    private String content;
    private String date;
    private String img;
    private String link;

    public RssNews() {

    }
    public RssNews(String title, String content, String date, String img, String link) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.img = img;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.date);
        dest.writeString(this.img);
        dest.writeString(this.link);
    }

    protected RssNews(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.date = in.readString();
        this.img = in.readString();
        this.link = in.readString();
    }

    public static final transient Parcelable.Creator<RssNews> CREATOR = new Parcelable.Creator<RssNews>() {
        public RssNews createFromParcel(Parcel source) {
            return new RssNews(source);
        }

        public RssNews[] newArray(int size) {
            return new RssNews[size];
        }
    };
}
