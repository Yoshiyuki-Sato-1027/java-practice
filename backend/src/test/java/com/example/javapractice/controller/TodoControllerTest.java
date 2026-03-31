package com.example.javapractice.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.javapractice.entity.Todo;
import com.example.javapractice.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TodoController.class)
@DisplayName("TodoController テスト")
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---- US1: タスク作成バリデーション ----

    @Test
    @DisplayName("[US1] POST /api/todos - 空タイトルで400 Bad Requestが返る")
    void createTodo_emptyTitle_returns400() throws Exception {
        when(todoService.create(any())).thenThrow(new IllegalArgumentException("タイトルは必須です"));

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("タイトルは必須です"));
    }

    @Test
    @DisplayName("[US1] POST /api/todos - 有効なタイトルで201 Createdが返る")
    void createTodo_validTitle_returns201() throws Exception {
        Todo created = buildTodo(1L, "買い物する", false);
        when(todoService.create(any())).thenReturn(created);

        mockMvc.perform(post("/api/todos").contentType(MediaType.APPLICATION_JSON).content("{\"title\":\"買い物する\"}"))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("買い物する")).andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    @DisplayName("[US1] GET /api/todos - タスク一覧が返る")
    void getAllTodos_returnsList() throws Exception {
        when(todoService.findAll()).thenReturn(List.of(
                buildTodo(1L, "タスクA", false),
                buildTodo(2L, "タスクB", true)));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("タスクA"))
                .andExpect(jsonPath("$[1].completed").value(true));
    }

    // ---- US2: 完了トグル ----

    @Test
    @DisplayName("[US2] PUT /api/todos/{id}/toggle - 未完了→完了にトグルできる")
    void toggleTodo_falseToTrue() throws Exception {
        Todo toggled = buildTodo(1L, "タスク", true);
        when(todoService.toggle(1L)).thenReturn(Optional.of(toggled));

        mockMvc.perform(put("/api/todos/1/toggle")).andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    @DisplayName("[US2] PUT /api/todos/{id}/toggle - 完了→未完了にトグルできる")
    void toggleTodo_trueToFalse() throws Exception {
        Todo toggled = buildTodo(1L, "タスク", false);
        when(todoService.toggle(1L)).thenReturn(Optional.of(toggled));

        mockMvc.perform(put("/api/todos/1/toggle")).andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    @DisplayName("[US2] PUT /api/todos/{id}/toggle - 存在しないIDで404が返る")
    void toggleTodo_notFound_returns404() throws Exception {
        when(todoService.toggle(999L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/todos/999/toggle"))
                .andExpect(status().isNotFound());
    }

    // ---- US3: タスク削除 ----

    @Test
    @DisplayName("[US3] DELETE /api/todos/{id} - タスクを削除すると204 No Contentが返る")
    void deleteTodo_returns204() throws Exception {
        doNothing().when(todoService).delete(1L);

        mockMvc.perform(delete("/api/todos/1")).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("[US3] DELETE /api/todos/{id} - 削除後にGETするとリストから消える")
    void deleteTodo_thenListIsEmpty() throws Exception {
        doNothing().when(todoService).delete(1L);
        when(todoService.findAll()).thenReturn(List.of());

        mockMvc.perform(delete("/api/todos/1")).andExpect(status().isNoContent());

        mockMvc.perform(get("/api/todos")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));
    }

    // ---- ヘルパー ----

    private Todo buildTodo(Long id, String title, boolean completed) {
        Todo todo = new Todo(title);
        todo.setId(id);
        todo.setCompleted(completed);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());
        return todo;
    }
}
