package com.mycompany.javatodolistapitemplatev1.infrastructure.persistence.repositories;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

@Repository
public class TodoRepositoryAsync implements ITodoRepositoryAsync {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryAsync(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CompletableFuture<List<Todo>> getTodoListAsync() {

        String sql = "SELECT * FROM todo";

        var entities = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Todo.class));

        return CompletableFuture.completedFuture(entities);
    }
}