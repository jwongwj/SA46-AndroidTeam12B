package com.example.sandy.getbooks;

public class BooksModel {
    private int BookID;
    private String Title;
    private int CategoryID;
    private String ISBN;
    private String Author;
    private int Stock;
    private double Price;

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public BooksModel(){

    }

    public BooksModel(int BookID, String Title, int CategoryID, String ISBN, String Author, int Stock, double Price){
        this.BookID = BookID;
        this.Title = Title;
        this.CategoryID = CategoryID;
        this.ISBN = ISBN;
        this.Author = Author;
        this.Stock = Stock;
        this.Price = Price;
    }

    public static BooksModel getBooks(int bookID){
        BooksModel a = new BooksModel(1, "title1", 1, "101", "test1", 10, 1);
        BooksModel b = new BooksModel(2, "title2", 1, "102", "test2", 10, 1);
        BooksModel c = new BooksModel(3, "title3", 1, "103", "test3", 10, 1);
        BooksModel d = new BooksModel(4, "title4", 1, "104", "test4", 10, 1);
        return a;
    }
}
