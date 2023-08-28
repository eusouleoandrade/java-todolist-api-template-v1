package com.mycompany.javatodolistapitemplatev1.infrastructure.persistence.repositories;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;
import com.mycompany.javatodolistapitemplatev1.shared.exceptions.AppException;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

@Repository
public class TodoRepositoryAsync implements ITodoRepositoryAsync {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(TodoRepositoryAsync.class);

    public TodoRepositoryAsync(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public CompletableFuture<List<Todo>> getTodoListAsync() {

        logger.info(String.format("Start repository %s > method getTodoListAsync.",
                TodoRepositoryAsync.class.getSimpleName()));

        String sql = "SELECT * FROM todo";

        try {

            var entities = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Todo.class));

            return CompletableFuture.completedFuture(entities);
        } catch (DataAccessException ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR_TXT() + " - Error: " + ex.getMessage(), ex);
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR_TXT(), ex);
        } finally {

            logger.info(String.format("Finishes repository %s > method getTodoListAsync.",
                    TodoRepositoryAsync.class.getSimpleName()));
        }
    }

    @Override
    public CompletableFuture<List<Todo>> getPaginatedTodoListsAsync(int pageSize, int pageNumber) {

        logger.info(String.format("Start repository %s > method getPaginatedTodoListsAsync.",
                TodoRepositoryAsync.class.getSimpleName()));

        int offset = (pageNumber - 1) * pageSize;

        String query = "SELECT id, title, done FROM todo ORDER BY id DESC LIMIT :pageSize OFFSET :offset";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("pageSize", pageSize)
                .addValue("offset", offset);

        try {

            var entities = namedParameterJdbcTemplate.query(query, params, new BeanPropertyRowMapper<>(Todo.class));

            return CompletableFuture.completedFuture(entities);

        } catch (DataAccessException ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR_TXT() + " - Error: " + ex.getMessage(), ex);
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR_TXT(), ex);
        } finally {

            logger.info(String.format("Finishes repository %s > method getPaginatedTodoListsAsync.",
                    TodoRepositoryAsync.class.getSimpleName()));
        }
    }

    @Override
    public CompletableFuture<Integer> getTotalRecordsAsync() {

        logger.info(String.format("Start repository %s > method getTotalRecordsAsync.",
                TodoRepositoryAsync.class.getSimpleName()));

        String query = "SELECT COUNT(id) FROM todo";

        try {

            Integer totalRecords = jdbcTemplate.queryForObject(query, Integer.class);

            return CompletableFuture.completedFuture(totalRecords);

        } catch (DataAccessException ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR_TXT() + " - Error: " + ex.getMessage(), ex);
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR_TXT(), ex);
        } finally {

            logger.info(String.format("Finishes repository %s > method getTotalRecordsAsync.",
                    TodoRepositoryAsync.class.getSimpleName()));
        }
    }
}