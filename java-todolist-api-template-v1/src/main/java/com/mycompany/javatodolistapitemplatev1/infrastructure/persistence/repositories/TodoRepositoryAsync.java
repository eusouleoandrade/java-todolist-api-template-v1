package com.mycompany.javatodolistapitemplatev1.infrastructure.persistence.repositories;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;
import com.mycompany.javatodolistapitemplatev1.shared.exceptions.AppException;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

@Repository
public class TodoRepositoryAsync implements ITodoRepositoryAsync {

    private final JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(TodoRepositoryAsync.class);

    public TodoRepositoryAsync(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CompletableFuture<List<Todo>> getTodoListAsync() {

        try {
            // Loger start
            logger.info(String.format("Start repository %s > method getTodoListAsync.",
                    TodoRepositoryAsync.class.getSimpleName()));

            // Sql query
            String sql = "SELECT * FROM todo";

            // entities
            var entities = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Todo.class));

            // Loger finishes successfully
            logger.info(String.format("Finishes successfully repository %s > method getTodoListAsync.",
                    TodoRepositoryAsync.class.getSimpleName()));

            // Return
            return CompletableFuture.completedFuture(entities);
        } catch (Exception e) {
            // Set AppException
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR_TXT(), e);
        }
    }
}