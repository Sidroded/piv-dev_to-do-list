package com.sidroded.todolist.user;

import java.util.List;

public class OpenDataClass {
    static List<String> maintitle;
    static List<String> title_context;

    public OpenDataClass(List<String> maintitle, List<String> title_context) {
        this.maintitle = maintitle;
        this.title_context = title_context;
    }

    public static List<String> getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(List<String> maintitle) {
        this.maintitle = maintitle;
    }

    public static List<String> getTitle_context() {
        return title_context;
    }

    public void setTitle_context(List<String> title_context) {
        this.title_context = title_context;
    }
}
