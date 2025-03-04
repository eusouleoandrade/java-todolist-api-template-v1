package com.mycompany.javatodolistapitemplatev1.infrastructure.persistence.repositories;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mycompany.javatodolistapitemplatev1.application.exceptions.AppException;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;
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

        logger.info("Start repository %s > method getTodoListAsync.".formatted(
                TodoRepositoryAsync.class.getSimpleName()));

        String sql = "SELECT * FROM todolistdb.todo";

        try {

            var entities = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Todo.class));

            return CompletableFuture.completedFuture(entities);
        } catch (DataAccessException ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR()[1] + " - Error: " + ex.getMessage(), ex);
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR()[1], ex);
        } finally {

            logger.info("Finishes repository %s > method getTodoListAsync.".formatted(
                    TodoRepositoryAsync.class.getSimpleName()));
        }
    }

    @Override
    public CompletableFuture<List<Todo>> getPaginatedTodoListsAsync(int pageSize, int pageNumber) {

        logger.info("Start repository %s > method getPaginatedTodoListsAsync.".formatted(
                TodoRepositoryAsync.class.getSimpleName()));

        int offset = (pageNumber - 1) * pageSize;

        String query = "SELECT id, title, done FROM todolistdb.todo ORDER BY id DESC LIMIT :pageSize OFFSET :offset";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("pageSize", pageSize)
                .addValue("offset", offset);

        try {

            var entities = namedParameterJdbcTemplate.query(query, params, new BeanPropertyRowMapper<>(Todo.class));

            return CompletableFuture.completedFuture(entities);

        } catch (DataAccessException ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR()[1] + " - Error: " + ex.getMessage(), ex);
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR()[1], ex);
        } finally {

            logger.info("Finishes repository %s > method getPaginatedTodoListsAsync.".formatted(
                    TodoRepositoryAsync.class.getSimpleName()));
        }
    }

    @Override
    public CompletableFuture<Integer> getTotalRecordsAsync() {

        logger.info("Start repository %s > method getTotalRecordsAsync.".formatted(
                TodoRepositoryAsync.class.getSimpleName()));

        String query = "SELECT COUNT(id) FROM todolistdb.todo";

        try {

            Integer totalRecords = jdbcTemplate.queryForObject(query, Integer.class);

            return CompletableFuture.completedFuture(totalRecords);

        } catch (DataAccessException ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR()[1] + " - Error: " + ex.getMessage(), ex);
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR()[1], ex);
        } finally {

            logger.info("Finishes repository %s > method getTotalRecordsAsync.".formatted(
                    TodoRepositoryAsync.class.getSimpleName()));
        }
    }

    @Override
    public CompletableFuture<Todo> getAsync(long id) {

        logger.info("Start repository %s > method getAsync.".formatted(
                TodoRepositoryAsync.class.getSimpleName()));

        String query = "SELECT * FROM todolistdb.todo WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        try {

            Todo entity = namedParameterJdbcTemplate.queryForObject(query, params,
                    new BeanPropertyRowMapper<>(Todo.class));

            return CompletableFuture.completedFuture(entity);

        } catch (EmptyResultDataAccessException ex) {

            return CompletableFuture.completedFuture(null);

        } catch (Exception ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR()[1] + " - Error: " + ex.getMessage(), ex);
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR()[1], ex);

        } finally {

            logger.info("Finishes repository %s > method getAsync.".formatted(
                    TodoRepositoryAsync.class.getSimpleName()));
        }
    }

    @Override
    public CompletableFuture<Todo> createAsync(Todo entity) {

        logger.info("Start repository %s > method createAsync.".formatted(
                TodoRepositoryAsync.class.getSimpleName()));

        String query = "INSERT INTO todolistdb.todo (title, done) VALUES (:title, :done)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", entity.getTitle())
                .addValue("done", entity.isDone());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {

            int affectedLines = namedParameterJdbcTemplate.update(query, params, keyHolder, new String[] { "id" });

            if (affectedLines > 0) {

                var generatedId = keyHolder.getKey().longValue();
                return getAsync(generatedId);

            } else {

                logger.warn("Failed to insert. Zero rows affected. Repository %s > method createAsync.".formatted(
                        TodoRepositoryAsync.class.getSimpleName()));

                return CompletableFuture.completedFuture(null);
            }

        } catch (Exception ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR()[1] + " - Error: " + ex.getMessage(), ex);
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR()[1], ex);

        } finally {

            logger.info("Finishes repository %s > method createAsync.".formatted(
                    TodoRepositoryAsync.class.getSimpleName()));
        }
    }

    @Override
    public CompletableFuture<Boolean> deleteAsync(long id) {

        logger.info("Start repository %s > method deleteAsync.".formatted(
                TodoRepositoryAsync.class.getSimpleName()));

        String sqlCommand = "DELETE FROM todolistdb.todo WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        try {

            int affectedLines = namedParameterJdbcTemplate.update(sqlCommand, params);

            return CompletableFuture.completedFuture(affectedLines > 0);

        } catch (Exception ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR()[1] + " - Error: " + ex.getMessage(), ex);
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR()[1], ex);

        } finally {

            logger.info("Finishes repository %s > method deleteAsync.".formatted(
                    TodoRepositoryAsync.class.getSimpleName()));
        }
    }

    @Override
    public CompletableFuture<Boolean> updateAsync(Todo entity) {

        logger.info("Start repository %s > method updateAsync.".formatted(
                TodoRepositoryAsync.class.getSimpleName()));

        String sqlCommand = "UPDATE todolistdb.todo SET title = :title, done = :done WHERE id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", entity.getTitle())
                .addValue("done", entity.isDone())
                .addValue("id", entity.getId());

        try {

            int affectedLines = namedParameterJdbcTemplate.update(sqlCommand, params);

            return CompletableFuture.completedFuture(affectedLines > 0);

        } catch (Exception ex) {

            logger.error(MsgUltil.DATA_BASE_SERVER_ERROR()[1] + " - Error: " + ex.getMessage(), ex);
            throw new AppException(MsgUltil.DATA_BASE_SERVER_ERROR()[1], ex);

        } finally {

            logger.info("Finishes repository %s > method updateAsync.".formatted(
                    TodoRepositoryAsync.class.getSimpleName()));
        }
    }
}