package com.db.bookstore.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Book implements Serializable {
    String isbn;
    String title;
    String author;
    String publisher;
    Date publish_time;
    String introduction;
    float price;
    int stock;

    public Book(String isbn,String title,String author,String publisher,float price,Date publish_time,String introduction,int stock){
        this.isbn=isbn;
        this.title=title;
        this.author=author;
        this.publisher=publisher;
        this.publish_time=publish_time;
        this.introduction=introduction;
        this.price=price;
        this.stock=stock;
    }

    public Book(String isbn,String title,String author,String publisher,float price){
        this(isbn,title,author,publisher,price,null,"",0);
    }

    public Book(){
        this("","","","",0);
    }

}

