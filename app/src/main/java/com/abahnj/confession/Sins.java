package com.abahnj.confession;

/**
 * Created by abahnj on 7/5/2016.
 */
public class Sins {
    int count;
    int custom;
    int deleted;
    String description;
    int edited;
    int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCustom() {
        return this.custom;
    }

    public void setCustom(int custom) {
        this.custom = custom;
    }

    public int getEdited() {
        return this.edited;
    }

    public void setEdited(int edited) {
        this.edited = edited;
    }

    public int getDeleted() {
        return this.deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = this.edited;
    }
}