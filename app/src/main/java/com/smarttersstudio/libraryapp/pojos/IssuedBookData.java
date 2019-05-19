package com.smarttersstudio.libraryapp.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssuedBookData {
    @SerializedName("id")
    @Expose
    private String bookId;

    @SerializedName("name")
    @Expose
    private String bookName;

    @SerializedName("timestamp")
    @Expose
    private String issueDate;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public IssuedBookData() {
    }

    public IssuedBookData(String bookId, String bookName, String issueDate) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.issueDate = issueDate;
    }
}
