package com.mycompany.javatodolistapitemplatev1.application.dtos.queries;

public class TodoQuery {

    private long id;

    private String title;

    private boolean done;

    public TodoQuery(long id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public TodoQuery() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
