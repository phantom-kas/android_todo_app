package com.example.to_do_app.dbModels;

public class CategoriesModel {
    private int id;
    private String category;
    private String created_at;

    public CategoriesModel(int id, String category, String created_at) {
        this.id = id;
        this.category = category;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}


