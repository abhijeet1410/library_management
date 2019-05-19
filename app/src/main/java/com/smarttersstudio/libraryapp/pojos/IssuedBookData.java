package com.smarttersstudio.libraryapp.pojos;

public class IssuedBookData {
    private String bookName,bookTitle,issuedDate;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public IssuedBookData() {
    }

    public IssuedBookData(String bookName, String bookTitle, String issuedDate) {
        this.bookName = bookName;
        this.bookTitle = bookTitle;
        this.issuedDate = issuedDate;
    }

}
