package com.example.mybudget.Models;

public class WishList {
    private int WishListId;
    private String title;
    private int cost;
    private int saved;
    private String image;
    //private int categoryId;

    /**
     * Model holding the info regarding wishes
     * @author Dawnie Safar
     */
    public WishList(){}
    public WishList(int WishListId, String title, int cost, int saved){
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

    public int getCost() {
        return cost;
    }

    public int getSaved(){
        return saved;
    }

    public String getImage() {return image; }


//    public int getCategoryId() {
//        return categoryId;
//    }

    public void setWishListId(int wishListId) {
        WishListId = wishListId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setSaved(int saved){
        this.saved = saved;
    }

    public void setImage(String image){ this.image = image; }

//    public void setCategoryId(int categoryId) {
//        this.categoryId = categoryId;
//    }
}
