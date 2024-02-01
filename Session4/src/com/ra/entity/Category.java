package com.ra.entity;

public class Category {
    private int CategoryID;
    private String CategoryName;
    private String Description;
    private int ParentCategoryID;

    public Category() {
    }

    public Category(int categoryID, String categoryName, String description, int parentCategoryID) {
        CategoryID = categoryID;
        CategoryName = categoryName;
        Description = description;
        ParentCategoryID = parentCategoryID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getParentCategoryID() {
        return ParentCategoryID;
    }

    public void setParentCategoryID(int parentCategoryID) {
        ParentCategoryID = parentCategoryID;
    }
}
