package com.mycompany.javatodolistapitemplatev1.infrastructure.persistence.repositories;

import com.mycompany.javatodolistapitemplatev1.application.exceptions.AppException;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"unchecked","rawtypes"})
class TodoRepositoryAsyncTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private TodoRepositoryAsync repository;

    private LogCaptor logCaptor;

    @BeforeEach
    void setUp() {
        // repository is injected with mocks
        logCaptor = LogCaptor.forClass(TodoRepositoryAsync.class);
    }

    @DisplayName("getTodoListAsync - should return list when DB returns rows")
    @Test
    void getTodoListAsync_shouldReturnList() {
        // Arranje
        var todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Title 1");
        todo.setDone(false);

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class)))
                .thenReturn(List.of(todo));

        // Act
        var result = repository.getTodoListAsync().join();

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(jdbcTemplate, times(1)).query(anyString(), any(BeanPropertyRowMapper.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method getTodoListAsync.",
                "Finishes repository TodoRepositoryAsync > method getTodoListAsync.");
    }

    @DisplayName("getTodoListAsync - should throw AppException on DataAccessException")
    @Test
    void getTodoListAsync_whenDataAccessException_shouldThrowAppException() {
        // Arranje
        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class)))
                .thenThrow(new DataAccessResourceFailureException("db error"));

        // Act & Assert (method throws AppException directly)
        assertThatThrownBy(() -> repository.getTodoListAsync())
                .isInstanceOf(AppException.class);

        verify(jdbcTemplate, times(1)).query(anyString(), any(BeanPropertyRowMapper.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method getTodoListAsync.",
                "Finishes repository TodoRepositoryAsync > method getTodoListAsync.");
        assertThat(logCaptor.getErrorLogs()).hasSize(1);
        assertThat(logCaptor.getErrorLogs().get(0)).contains("Database server error.", "db error");
    }

    @DisplayName("getPaginatedTodoListsAsync - should return paged list")
    @Test
    void getPaginatedTodoListsAsync_shouldReturnPagedList() {
        // Arranje
        var todo = new Todo();
        todo.setId(2L);
        todo.setTitle("Title 2");
        todo.setDone(true);

        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(List.of(todo));

        // Act
        var result = repository.getPaginatedTodoListsAsync(10, 1).join();

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(2L);
        verify(namedParameterJdbcTemplate, times(1)).query(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method getPaginatedTodoListsAsync.",
                "Finishes repository TodoRepositoryAsync > method getPaginatedTodoListsAsync.");
    }

    @DisplayName("getTotalRecordsAsync - should return count")
    @Test
    void getTotalRecordsAsync_shouldReturnCount() {
        // Arranje
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(5);

        // Act
        var result = repository.getTotalRecordsAsync().join();

        // Assert
        assertThat(result).isEqualTo(5);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method getTotalRecordsAsync.",
                "Finishes repository TodoRepositoryAsync > method getTotalRecordsAsync.");
    }

    @DisplayName("getAsync - should return entity when found")
    @Test
    void getAsync_shouldReturnEntityWhenFound() {
        // Arranje
        var todo = new Todo();
        todo.setId(3L);
        todo.setTitle("Title 3");
        todo.setDone(false);

        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenReturn(todo);

        // Act
        var result = repository.getAsync(3L).join();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3L);
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method getAsync.",
                "Finishes repository TodoRepositoryAsync > method getAsync.");
    }

    @DisplayName("getAsync - should return null when not found")
    @Test
    void getAsync_whenNotFound_shouldReturnNull() {
        // Arranje
        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act
        var result = repository.getAsync(99L).join();

        // Assert
        assertThat(result).isNull();
        verify(namedParameterJdbcTemplate, times(1)).queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method getAsync.",
                "Finishes repository TodoRepositoryAsync > method getAsync.");
    }

    @DisplayName("createAsync - should insert and return created entity")
    @Test
    void createAsync_shouldInsertAndReturnCreatedEntity() {
        // Arranje
        // prepare a spy so we can stub getAsync called inside createAsync
        var repoSpy = Mockito.spy(new TodoRepositoryAsync(jdbcTemplate, namedParameterJdbcTemplate));

        var toInsert = new Todo();
        toInsert.setTitle("New Title");
        toInsert.setDone(false);

        var created = new Todo();
        created.setId(10L);
        created.setTitle(toInsert.getTitle());
        created.setDone(toInsert.isDone());

        // when update is called, populate the KeyHolder with generated id and return 1
        doAnswer(invocation -> {
            KeyHolder kh = invocation.getArgument(2);
            if (kh instanceof GeneratedKeyHolder) {
                ((GeneratedKeyHolder) kh).getKeyList().add(Map.of("id", created.getId()));
            }
            return 1;
        }).when(namedParameterJdbcTemplate).update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class));

        // when getAsync called with generated id, return created entity
        doReturn(CompletableFuture.completedFuture(created)).when(repoSpy).getAsync(created.getId());

        // Act
        var result = repoSpy.createAsync(toInsert).join();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getTitle()).isEqualTo("New Title");

        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class));
        verify(repoSpy, times(1)).getAsync(created.getId());

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method createAsync.",
                "Finishes repository TodoRepositoryAsync > method createAsync.");
    }

    @DisplayName("createAsync - should return null and warn when zero rows affected")
    @Test
    void createAsync_whenZeroRowsAffected_shouldReturnNullAndLogWarn() {
        // Arranje
        var toInsert = new Todo();
        toInsert.setTitle("New Title");
        toInsert.setDone(false);

        // simulate update returning 0 rows
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class)))
                .thenReturn(0);

        // Act
        var result = repository.createAsync(toInsert).join();

        // Assert
        assertThat(result).isNull();
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class));

        // Log Assert - should contain warn about zero rows
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method createAsync.",
                "Finishes repository TodoRepositoryAsync > method createAsync.");
        assertThat(logCaptor.getWarnLogs()).hasSize(1);
        assertThat(logCaptor.getWarnLogs().get(0)).contains("Failed to insert. Zero rows affected.");
    }

    @DisplayName("createAsync - should throw AppException and log error when update throws")
    @Test
    void createAsync_whenUpdateThrows_shouldThrowAppExceptionAndLogError() {
        // Arranje
        var toInsert = new Todo();
        toInsert.setTitle("New Title");
        toInsert.setDone(false);

        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class)))
                .thenThrow(new DataAccessResourceFailureException("db error"));

        // Act & Assert
        assertThatThrownBy(() -> repository.createAsync(toInsert))
                .isInstanceOf(AppException.class);

        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class), any(String[].class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method createAsync.",
                "Finishes repository TodoRepositoryAsync > method createAsync.");
        assertThat(logCaptor.getErrorLogs()).hasSize(1);
        assertThat(logCaptor.getErrorLogs().get(0)).contains("Database server error.", "db error");
    }

    @DisplayName("deleteAsync - should return true when rows affected")
    @Test
    void deleteAsync_shouldReturnTrueWhenRowsAffected() {
        // Arranje
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(1);

        // Act
        var result = repository.deleteAsync(5L).join();

        // Assert
        assertThat(result).isTrue();
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method deleteAsync.",
                "Finishes repository TodoRepositoryAsync > method deleteAsync.");
    }

    @DisplayName("deleteAsync - should return false when no rows affected")
    @Test
    void deleteAsync_shouldReturnFalseWhenNoRowsAffected() {
        // Arranje
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(0);

        // Act
        var result = repository.deleteAsync(5L).join();

        // Assert
        assertThat(result).isFalse();
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method deleteAsync.",
                "Finishes repository TodoRepositoryAsync > method deleteAsync.");
    }

    @DisplayName("deleteAsync - should throw AppException and log error when update throws")
    @Test
    void deleteAsync_whenUpdateThrows_shouldThrowAppExceptionAndLogError() {
        // Arranje
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenThrow(new DataAccessResourceFailureException("db error"));

        // Act & Assert
        assertThatThrownBy(() -> repository.deleteAsync(1L))
                .isInstanceOf(AppException.class);

        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method deleteAsync.",
                "Finishes repository TodoRepositoryAsync > method deleteAsync.");
        assertThat(logCaptor.getErrorLogs()).hasSize(1);
        assertThat(logCaptor.getErrorLogs().get(0)).contains("Database server error.", "db error");
    }

    @DisplayName("updateAsync - should return true when updated")
    @Test
    void updateAsync_shouldReturnTrueWhenUpdated() {
        // Arranje
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(1);

        var toUpdate = new Todo();
        toUpdate.setId(7L);
        toUpdate.setTitle("Updated");
        toUpdate.setDone(true);

        // Act
        var result = repository.updateAsync(toUpdate).join();

        // Assert
        assertThat(result).isTrue();
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method updateAsync.",
                "Finishes repository TodoRepositoryAsync > method updateAsync.");
    }

    @DisplayName("updateAsync - should return false when nothing updated")
    @Test
    void updateAsync_shouldReturnFalseWhenNothingUpdated() {
        // Arranje
        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(0);

        var toUpdate = new Todo();
        toUpdate.setId(8L);
        toUpdate.setTitle("Updated");
        toUpdate.setDone(false);

        // Act
        var result = repository.updateAsync(toUpdate).join();

        // Assert
        assertThat(result).isFalse();
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method updateAsync.",
                "Finishes repository TodoRepositoryAsync > method updateAsync.");
    }

    @DisplayName("updateAsync - should throw AppException and log error when update throws")
    @Test
    void updateAsync_whenUpdateThrows_shouldThrowAppExceptionAndLogError() {
        // Arranje
        var toUpdate = new Todo();
        toUpdate.setId(7L);
        toUpdate.setTitle("Updated");
        toUpdate.setDone(true);

        when(namedParameterJdbcTemplate.update(anyString(), any(MapSqlParameterSource.class)))
                .thenThrow(new DataAccessResourceFailureException("db error"));

        // Act & Assert
        assertThatThrownBy(() -> repository.updateAsync(toUpdate))
                .isInstanceOf(AppException.class);

        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(MapSqlParameterSource.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method updateAsync.",
                "Finishes repository TodoRepositoryAsync > method updateAsync.");
        assertThat(logCaptor.getErrorLogs()).hasSize(1);
        assertThat(logCaptor.getErrorLogs().get(0)).contains("Database server error.", "db error");
    }

    @DisplayName("getTotalRecordsAsync - should throw AppException on error")
    @Test
    void getTotalRecordsAsync_whenError_shouldThrowAppException() {
        // Arranje
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class)))
                .thenThrow(new DataAccessResourceFailureException("db error"));

        // Act & Assert (method throws AppException directly)
        assertThatThrownBy(() -> repository.getTotalRecordsAsync())
                .isInstanceOf(AppException.class);

        verify(jdbcTemplate, times(1)).queryForObject(anyString(), eq(Integer.class));
    }

    @DisplayName("getPaginatedTodoListsAsync - should throw AppException on DataAccessException")
    @Test
    void getPaginatedTodoListsAsync_whenDataAccessException_shouldThrowAppException() {
        // Arranje
        when(namedParameterJdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenThrow(new DataAccessResourceFailureException("db error"));

        // Act & Assert
        assertThatThrownBy(() -> repository.getPaginatedTodoListsAsync(10, 1))
                .isInstanceOf(AppException.class);

        verify(namedParameterJdbcTemplate, times(1)).query(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method getPaginatedTodoListsAsync.",
                "Finishes repository TodoRepositoryAsync > method getPaginatedTodoListsAsync.");
        assertThat(logCaptor.getErrorLogs()).hasSize(1);
        assertThat(logCaptor.getErrorLogs().get(0)).contains("Database server error.", "db error");
    }

    @DisplayName("getAsync - should throw AppException on unexpected exception")
    @Test
    void getAsync_whenUnexpectedException_shouldThrowAppExceptionAndLogError() {
        // Arranje
        when(namedParameterJdbcTemplate.queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class)))
                .thenThrow(new DataAccessResourceFailureException("db error"));

        // Act & Assert
        assertThatThrownBy(() -> repository.getAsync(5L))
                .isInstanceOf(AppException.class);

        verify(namedParameterJdbcTemplate, times(1)).queryForObject(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class));

        // Log Assert
        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start repository TodoRepositoryAsync > method getAsync.",
                "Finishes repository TodoRepositoryAsync > method getAsync.");
        assertThat(logCaptor.getErrorLogs()).hasSize(1);
        assertThat(logCaptor.getErrorLogs().get(0)).contains("Database server error.", "db error");
    }
}
