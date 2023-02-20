package com.sidroded.todolist.note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class
NoteModel {
    private String tittle;
    private String description;
    private String date;
    private String time;
    private String friend;
    private String category;
    private long millis;

    public String getFilename() {
        return filename;
    }

    //тут нужно добавить файлы еще
    String filename;
    public String getTittle() {
        return tittle;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getFriend() {
        return friend;
    }

    public String getCategory() {
        return category;
    }

    public long getMillis() {
        return millis;
    }

    public NoteModel() {
    }

    public NoteModel(String tittle, String description, String date, String time,String friend, String category,String filename, long millis) {
        this.tittle = tittle;
        this.description = description;
        this.date = date;
        this.time = time;
        this.friend = friend;
        this.category = category;
        this.filename=filename;
        this.millis = millis;
    }
}
