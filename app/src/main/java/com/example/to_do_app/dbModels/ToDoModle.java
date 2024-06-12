package com.example.to_do_app.dbModels;

public class ToDoModle {
    private int id;
    private String todo;
    private int categoryId;
    private String createdAt;
private boolean isDone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getTodo() {
        return todo;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isDone() {
        return isDone;
    }

    public ToDoModle(int id, String todo, int categoryId, String createdAt, boolean isDone) {
        this.id = id;
        this.todo = todo;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.isDone = isDone;
    }
}
