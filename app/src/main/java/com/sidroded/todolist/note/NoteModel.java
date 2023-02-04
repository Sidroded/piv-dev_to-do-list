package com.sidroded.todolist.note;

public class NoteModel {
    String tittle;
    String description;
    String date;
    String time;
    String friend;
    String category;
    //тут нужно добавить файлы еще

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


    public NoteModel() {
    }

    public NoteModel(String tittle, String description, String date, String time, String friend, String category) {
        this.tittle = tittle;
        this.description = description;
        this.date = date;
        this.time = time;
        this.friend = friend;
        this.category = category;
    }
}
