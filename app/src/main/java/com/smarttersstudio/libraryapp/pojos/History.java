package com.smarttersstudio.libraryapp.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("book_id")
    @Expose
    private String bookId;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    public History(String action, String name, String bookId, String timestamp) {
        this.action = action;
        this.name = name;
        this.bookId = bookId;
        this.timestamp = timestamp;
    }

    public History() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
