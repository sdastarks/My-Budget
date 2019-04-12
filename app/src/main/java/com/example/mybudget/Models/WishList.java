package com.example.mybudget.Models;

public class WishList {
    private int WishListId;
    private String title;
    private float cost;
    private float saved;
    //private int categoryId;

    /**
     * Model holding the info regarding wishes
     * @author Dawnie Safar
     */
    public WishList(){}
    public WishList(int WishListId, String title, float cost, float saved){
        this.WishListId = WishListId;
        this.title = title;
        this.cost = cost;
        this.saved = saved;
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

    public float getSaved(){
        return saved;
    }


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

    public void setSaved(float saved){
        this.saved = saved;
    }

//    public void setCategoryId(int categoryId) {
//        this.categoryId = categoryId;
//    }
}
