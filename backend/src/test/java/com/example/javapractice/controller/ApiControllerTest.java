package com.example.javapractice.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * ApiControllerのJUnit 5テスト
 *
 * @WebMvcTestアノテーションを使用して、コントローラー層のみをテスト MockMvcを使ってHTTPリクエストをシミュレート
 */
@WebMvcTest(ApiController.class)
@DisplayName("ApiController テスト")
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/hello - 正常にHelloメッセージを返す")
    void testHelloEndpoint() throws Exception {
        // when: /api/helloにGETリクエストを送信
        mockMvc.perform(get("/api/hello"))
                // then: ステータスコードが200 OKである
                .andExpect(status().isOk())
                // and: Content-Typeがapplication/jsonである
                .andExpect(content().contentType("application/json"))
                // and: JSONレスポンスが期待通りである
                .andExpect(jsonPath("$.message").value("Hello, World!"))
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("GET /api/greet - デフォルトパラメータでGreetメッセージを返す")
    void testGreetWithoutParameter() throws Exception {
        // when: パラメータなしで/api/greetにGETリクエストを送信
        mockMvc.perform(get("/api/greet"))
                // then: ステータスコードが200 OKである
                .andExpect(status().isOk())
                // and: デフォルトのGuest名でメッセージが返される
                .andExpect(jsonPath("$.message").value("Hello, Guest!"))
                .andExpect(jsonPath("$.status").value("success"));
    }

    @ParameterizedTest
    @DisplayName("GET /api/greet?name={name} - カスタム名でGreetメッセージを返す")
    @CsvSource({"Alice, 'Hello, Alice!'", "Bob, 'Hello, Bob!'", "太郎, 'Hello, 太郎!'", "123, 'Hello, 123!'"})
    void testGreetWithParameter(String name, String expectedMessage) throws Exception {
        // when: nameパラメータ付きで/api/greetにGETリクエストを送信
        mockMvc.perform(get("/api/greet").param("name", name))
                // then: ステータスコードが200 OKである
                .andExpect(status().isOk())
                // and: 指定した名前でメッセージが返される
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("GET /api/time - 現在時刻情報を返す")
    void testTimeEndpoint() throws Exception {
        // when: /api/timeにGETリクエストを送信
        mockMvc.perform(get("/api/time"))
                // then: ステータスコードが200 OKである
                .andExpect(status().isOk())
                // and: JSONレスポンスに必要なフィールドが含まれる
                .andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.time").exists())
                // and: 日付フォーマットが正しい（YYYY-MM-DD形式）
                .andExpect(jsonPath("$.date").value(matchesPattern("\\d{4}-\\d{2}-\\d{2}")))
                // and: 時刻フォーマットが正しい（HH:MM:SS形式）
                .andExpect(jsonPath("$.time").value(matchesPattern("\\d{2}:\\d{2}:\\d{2}.*")));
    }

    @Test
    @DisplayName("GET /api/status - システムステータス情報を返す")
    void testStatusEndpoint() throws Exception {
        // when: /api/statusにGETリクエストを送信
        mockMvc.perform(get("/api/status"))
                // then: ステータスコードが200 OKである
                .andExpect(status().isOk())
                // and: JSONレスポンスにステータス情報が含まれる
                .andExpect(jsonPath("$.status").value("running"))
                // and: メモリ情報が含まれる
                .andExpect(jsonPath("$.memory").exists()).andExpect(jsonPath("$.memory.total").isNumber())
                .andExpect(jsonPath("$.memory.used").isNumber()).andExpect(jsonPath("$.memory.free").isNumber())
                // and: メモリの値が正の数である
                .andExpect(jsonPath("$.memory.total").value(greaterThan(0)))
                .andExpect(jsonPath("$.memory.used").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.memory.free").value(greaterThanOrEqualTo(0)));
    }

    @ParameterizedTest
    @DisplayName("すべてのエンドポイントがContent-Type: application/jsonを返す")
    @ValueSource(strings = {"/api/hello", "/api/greet", "/api/time", "/api/status"})
    void testContentTypeIsJson(String endpoint) throws Exception {
        // when & then: 各エンドポイントがJSONを返す
        mockMvc.perform(get(endpoint)).andExpect(status().isOk()).andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("GET /api/greet - 空文字列のnameパラメータでも動作する")
    void testGreetWithEmptyName() throws Exception {
        // when: 空文字列のnameパラメータで/api/greetにGETリクエストを送信
        // Spring MVCは空文字列のパラメータをデフォルト値に置き換える場合がある
        mockMvc.perform(get("/api/greet").param("name", ""))
                // then: ステータスコードが200 OKである
                .andExpect(status().isOk())
                // and: メッセージが返される（空文字列またはデフォルト値）
                .andExpect(jsonPath("$.message").exists()).andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    @DisplayName("GET /api/health - ステータスUPを返す")
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/health")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.version").exists()).andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("GET /api/greet - 特殊文字を含むnameパラメータでも動作する")
    void testGreetWithSpecialCharacters() throws Exception {
        // when: 特殊文字を含むnameで/api/greetにGETリクエストを送信
        mockMvc.perform(get("/api/greet").param("name", "John Doe <script>alert('test')</script>"))
                // then: ステータスコードが200 OKである
                .andExpect(status().isOk())
                // and: 特殊文字がそのまま含まれる
                .andExpect(jsonPath("$.message").value("Hello, John Doe <script>alert('test')</script>!"))
                .andExpect(jsonPath("$.status").value("success"));
    }
}
