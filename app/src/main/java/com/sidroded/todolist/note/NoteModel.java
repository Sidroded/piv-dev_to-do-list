package com.sidroded.todolist.note;

import java.util.Collections;
import java.util.List;

public class
NoteModel {
    String tittle;
    String description;
    String date;
    String time;
    List<String> friend;
    String category;

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

    public List<String> getFriend() {
        return friend;
    }

    public String getCategory() {
        return category;
    }


    public NoteModel() {
    }

    public NoteModel(String tittle, String description, String date, String time, String friend, String category,String filename) {
        this.tittle = tittle;
        this.description = description;
        this.date = date;
        this.time = time;
        this.friend = Collections.singletonList(friend);
        this.category = category;
        this.filename=filename;
    }
}
