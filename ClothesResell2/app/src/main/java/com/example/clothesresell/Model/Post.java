package com.example.clothesresell.Model;

public class Post {
    private String postid;
    private String postimage;
    private String description;
    private String price;
    private String publisher;

    public Post() {
    }

    public Post(String postid, String postimage, String description, String price, String publisher) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.price = price;
        this.publisher = publisher;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
