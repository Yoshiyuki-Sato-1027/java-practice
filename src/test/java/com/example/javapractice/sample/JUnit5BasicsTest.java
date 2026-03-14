package com.example.javapractice.sample;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5とAssertJの基本的な機能を学ぶためのサンプルテスト
 *
 * JUnit 5の特徴:
 * - @Test, @BeforeEach, @AfterEach などのアノテーションでテストを構成
 * - AssertJの流暢なAPI (Fluent API) で読みやすいアサーション
 * - @ParameterizedTestでデータ駆動テストが簡単に書ける
 * - @DisplayNameでテスト名を日本語で記述可能
 */
@DisplayName("JUnit 5 基礎テスト")
class JUnit5BasicsTest {

    private List<String> testList;

    @BeforeEach
    void setUp() {
        // 各テストの前に実行される
        testList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        // 各テストの後に実行される
        testList.clear();
    }

    @Test
    @DisplayName("文字列の連結テスト")
    void testStringConcatenation() {
        // given: 2つの文字列を用意
        String str1 = "Hello";
        String str2 = "World";

        // when: 2つの文字列を連結する
        String result = str1 + " " + str2;

        // then: 期待する結果になる（JUnit標準アサーション）
        assertEquals("Hello World", result);
        assertEquals(11, result.length());

        // AssertJのアサーション（より読みやすい）
        assertThat(result)
                .isEqualTo("Hello World")
                .hasSize(11)
                .startsWith("Hello")
                .endsWith("World")
                .contains(" ");
    }

    @Test
    @DisplayName("リストの操作テスト")
    void testListOperations() {
        // given: 空のリストを用意（@BeforeEachで初期化済み）

        // when: 要素を追加する
        testList.add("apple");
        testList.add("banana");
        testList.add("orange");

        // then: リストのサイズと内容を検証（AssertJ）
        assertThat(testList)
                .hasSize(3)
                .contains("banana")
                .containsExactly("apple", "banana", "orange")
                .doesNotContain("grape");

        assertThat(testList.get(0)).isEqualTo("apple");
    }

    @Test
    @DisplayName("例外が発生することを検証")
    void testExceptionIsThrown() {
        // when & then: 0で割ると ArithmeticException が発生する
        assertThrows(ArithmeticException.class, () -> {
            int result = 10 / 0;
        });

        // AssertJでの例外検証（より詳細な検証が可能）
        assertThatThrownBy(() -> {
                    int result = 10 / 0;
                })
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("/ by zero");
    }

    @ParameterizedTest
    @DisplayName("パラメータ化テスト - 数値の2乗")
    @CsvSource({
            "1, 1",
            "2, 4",
            "3, 9",
            "5, 25",
            "10, 100"
    })
    void testPowerOfTwo(int input, int expected) {
        // when: 2乗を計算
        double result = Math.pow(input, 2);

        // then: 期待値と一致する
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("パラメータ化テスト - 最大値の計算")
    @CsvSource({
            "5, 3, 5",
            "-5, -3, -3",
            "7, 7, 7",
            "0, 5, 5"
    })
    void testMax(int a, int b, int expected) {
        // when: 最大値を計算
        int result = Math.max(a, b);

        // then: 期待値と一致する
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("文字列の検証（複数のアサーション）")
    void testStringAssertions() {
        // given: テスト対象の文字列
        String text = "JUnit 5 Framework";

        // then: 複数の条件を満たす
        assertAll(
                () -> assertTrue(text.startsWith("JUnit")),
                () -> assertTrue(text.endsWith("Framework")),
                () -> assertTrue(text.contains(" ")),
                () -> assertEquals(17, text.length()),
                () -> assertFalse(text.isEmpty())
        );

        // AssertJでの検証（より読みやすい）
        assertThat(text)
                .startsWith("JUnit")
                .endsWith("Framework")
                .contains(" ")
                .hasSize(17)
                .isNotEmpty();
    }

    @Test
    @DisplayName("コレクションの検証")
    void testCollectionAssertions() {
        // given: 数値のリスト
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // then: コレクションの内容を検証
        assertThat(numbers)
                .hasSize(5)
                .containsExactly(1, 2, 3, 4, 5)
                .allMatch(n -> n > 0)  // すべての要素が0より大きい
                .anyMatch(n -> n % 2 == 0);  // 偶数が少なくとも1つある

        // 合計値の検証
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        assertThat(sum).isEqualTo(15);
    }

    @ParameterizedTest
    @DisplayName("文字列が空でないことを検証")
    @ValueSource(strings = {"apple", "banana", "orange"})
    void testNonEmptyStrings(String fruit) {
        // then: 文字列が空でないことを検証
        assertThat(fruit)
                .isNotNull()
                .isNotEmpty()
                .isNotBlank();
    }

    @Test
    @DisplayName("nullチェック")
    void testNullChecks() {
        // given
        String nullString = null;
        String nonNullString = "test";

        // then
        assertThat(nullString).isNull();
        assertThat(nonNullString).isNotNull();

        // JUnit標準のアサーション
        assertNull(nullString);
        assertNotNull(nonNullString);
    }

    @Nested
    @DisplayName("ネストされたテストクラス")
    class NestedTests {

        @Test
        @DisplayName("ネストされたテスト1")
        void nestedTest1() {
            assertThat(1 + 1).isEqualTo(2);
        }

        @Test
        @DisplayName("ネストされたテスト2")
        void nestedTest2() {
            assertThat("test").isEqualTo("test");
        }
    }
}
