package com.example.mybudget.Models;

public class WishList {
    private int WishListId;
    private String title;
    private float cost;
    //private Date date;
    //private int categoryId;

    public WishList(){}
    public WishList(int WishListId, String title, float cost){
        this.WishListId = WishListId;
        this.title = title;
        this.cost = cost;
        //this.date = date;
        //this.categoryId = categoryId;
    }

    public int getWishListId() {
        return WishListId;
    }

    public String getTitle() {
        return title;
    }

    public float getCost() {
        return cost;
    }

//    public Date getDate() {
//        return date;
//    }

//    public int getCategoryId() {
//        return categoryId;
//    }

    public void setWishListId(int wishListId) {
        WishListId = wishListId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

//    public void setDate(Date date) {
//        this.date = date;
//    }

//    public void setCategoryId(int categoryId) {
//        this.categoryId = categoryId;
//    }
}
