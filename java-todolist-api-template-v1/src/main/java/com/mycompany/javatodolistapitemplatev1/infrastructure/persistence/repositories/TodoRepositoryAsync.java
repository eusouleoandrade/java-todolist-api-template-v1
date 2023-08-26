package com.mycompany.javatodolistapitemplatev1.infrastructure.persistence.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

@Repository
public class TodoRepositoryAsync implements ITodoRepositoryAsync {

    // @Override
    // public CompletableFuture<List<Todo>> getTodoListAsync() {

    // List<Todo> entities = new ArrayList<Todo>();

    // entities.add(new Todo(1, "Todo one", true));
    // entities.add(new Todo(2, "Todo two", false));
    // entities.add(new Todo(1, "Todo three", true));

    // return CompletableFuture.completedFuture(entities);
    // }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TodoRepositoryAsync(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CompletableFuture<List<Todo>> getTodoListAsync() {

        String sql = "SELECT * FROM todo";

        List<Todo> entities = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Todo.class));

        return CompletableFuture.completedFuture(entities);
    }
}