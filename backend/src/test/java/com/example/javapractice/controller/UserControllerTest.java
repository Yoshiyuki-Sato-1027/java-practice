package com.example.javapractice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.javapractice.entity.User;
import com.example.javapractice.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@DisplayName("UserController テスト")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("GET /api/users - ユーザー一覧を返す")
    void getAll_returnsUserList() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setCreatedAt(LocalDateTime.now());

        when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].email").value("alice@example.com"));
    }

    @Test
    @DisplayName("GET /api/users - ユーザーが0件の場合は空配列を返す")
    void getAll_returnsEmptyList() throws Exception {
        when(userService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("GET /api/users/{id} - 存在するIDでユーザーを返す")
    void getById_returnsUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@example.com");

        when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    @DisplayName("GET /api/users/{id} - 存在しないIDで404を返す")
    void getById_returnsNotFound() throws Exception {
        when(userService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/users - ユーザーを作成して201を返す")
    void create_returnsCreated() throws Exception {
        User created = new User();
        created.setId(1L);
        created.setName("Bob");
        created.setEmail("bob@example.com");
        created.setCreatedAt(LocalDateTime.now());

        when(userService.create(any(User.class))).thenReturn(created);

        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Bob\",\"email\":\"bob@example.com\"}")).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("Bob"));
    }

    @Test
    @DisplayName("DELETE /api/users/{id} - ユーザーを削除して204を返す")
    void delete_returnsNoContent() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/users/1")).andExpect(status().isNoContent());
    }
}
