package com.mycompany.javatodolistapitemplatev1.domain.entities;

import com.mycompany.javatodolistapitemplatev1.domain.common.BaseEntity;

public class Todo extends BaseEntity<Long> {

    private String title;

    private boolean done;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Todo(long id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public Todo() {
    }
}