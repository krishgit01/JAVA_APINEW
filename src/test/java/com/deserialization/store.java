package com.deserialization;

import java.util.List;

public class store {

    List<Book1> book;

    public store(List<Book1> book) {
        this.book = book;
    }

    public store(){

    }

    public List<Book1> getBook() {
        return book;
    }

    public void setBook(List<Book1> book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Store{" +
                "book=" + book +
                '}';
    }
}
