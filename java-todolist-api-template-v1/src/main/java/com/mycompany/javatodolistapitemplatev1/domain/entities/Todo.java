package com.mycompany.javatodolistapitemplatev1.domain.entities;

import com.mycompany.javatodolistapitemplatev1.domain.common.BaseEntity;

public class Todo extends BaseEntity<Long> {

    private final String title;

    private final boolean done;

    public Todo(long id, String title, boolean done) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }
}