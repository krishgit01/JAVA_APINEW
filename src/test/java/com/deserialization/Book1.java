package com.deserialization;

public class Book1 {

    String author;
    String category;
    double price;
    String title;
    String isbn;

    public Book1(String author, String category, double price, String title, String isbn) {
        this.author = author;
        this.category = category;
        this.price = price;
        this.title = title;
        this.isbn = isbn;
    }

    public Book1() {

    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book1{" +
                "author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
