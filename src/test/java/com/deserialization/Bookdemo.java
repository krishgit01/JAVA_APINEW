package com.deserialization;

import javax.mail.Store;

public class Bookdemo {
    Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Bookdemo(Store store) {
        this.store = store;
    }

    public Bookdemo() {

    }

    @Override
    public String toString() {
        return "Bookdemo{" +
                "store=" + store +
                '}';
    }
}
