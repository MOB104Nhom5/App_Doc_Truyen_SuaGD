package com.example.app_doc_truyen.Model;

import com.google.gson.Gson;

public class Comic {
    public String _id;
    public String Name;
    public String Logo;
    public String Author;
    public String Category;
    public String Description;
    public String DateUp;
    private boolean TrangThai;
    public Comic() {
    }

    public Comic(String _id, String name, String logo, String author, String description, String dateUp, boolean trangThai) {
        this._id = _id;
        Name = name;
        Logo = logo;
        Author = author;
        Description = description;
        DateUp = dateUp;
        TrangThai = trangThai;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDateUp() {
        return DateUp;
    }

    public void setDateUp(String dateUp) {
        DateUp = dateUp;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean trangThai) {
        TrangThai = trangThai;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
