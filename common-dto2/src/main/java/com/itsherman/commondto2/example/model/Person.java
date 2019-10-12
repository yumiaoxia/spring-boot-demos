package com.itsherman.commondto2.example.model;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-26
 */
public class Person {

    private String name;

    private Book[] books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }
}
