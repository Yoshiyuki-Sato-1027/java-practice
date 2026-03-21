package com.example.javapractice.repository;

import static org.assertj.core.api.Assertions.*;

import com.example.javapractice.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

/**
 * UserRepositoryのJUnit 5テスト
 *
 * @DataJpaTestアノテーションを使用して、JPA関連のコンポーネントのみをロード テスト用のインメモリH2データベースを使用
 *                                              各テストメソッド後に自動的にロールバック
 */
@DataJpaTest
@DisplayName("UserRepository テスト")
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        // 各テストの前に実行される（必要に応じて初期データを準備）
    }

    @Test
    @DisplayName("新しいユーザーを保存できる")
    void testSaveNewUser() {
        // given: 新しいユーザーオブジェクトを作成
        User user = new User("John Doe", "john@example.com");

        // when: ユーザーを保存
        User savedUser = userRepository.save(user);

        // then: 保存されたユーザーにIDが割り当てられる
        assertThat(savedUser.getId()).isNotNull();

        // and: 保存されたデータが正しい
        assertThat(savedUser.getName()).isEqualTo("John Doe");
        assertThat(savedUser.getEmail()).isEqualTo("john@example.com");

        // and: 作成日時と更新日時が自動設定される
        assertThat(savedUser.getCreatedAt()).isNotNull();
        assertThat(savedUser.getUpdatedAt()).isNotNull();
        assertThat(savedUser.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedUser.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("IDでユーザーを検索できる")
    void testFindById() {
        // given: ユーザーをデータベースに保存
        User user = new User("Jane Smith", "jane@example.com");
        User savedUser = entityManager.persistAndFlush(user);
        Long userId = savedUser.getId();

        // when: IDで検索
        Optional<User> foundUser = userRepository.findById(userId);

        // then: ユーザーが見つかる
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Jane Smith");
        assertThat(foundUser.get().getEmail()).isEqualTo("jane@example.com");
    }

    @Test
    @DisplayName("存在しないIDで検索した場合はOptional.emptyが返る")
    void testFindByIdNotFound() {
        // when: 存在しないIDで検索
        Optional<User> foundUser = userRepository.findById(999L);

        // then: 空のOptionalが返る
        assertThat(foundUser).isEmpty();
    }

    @Test
    @DisplayName("メールアドレスでユーザーを検索できる")
    void testFindByEmail() {
        // given: 複数のユーザーをデータベースに保存
        entityManager.persistAndFlush(new User("Alice", "alice@example.com"));
        entityManager.persistAndFlush(new User("Bob", "bob@example.com"));
        entityManager.persistAndFlush(new User("Charlie", "charlie@example.com"));

        // when: メールアドレスで検索
        Optional<User> foundUser = userRepository.findByEmail("bob@example.com");

        // then: 該当するユーザーが見つかる
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Bob");
    }

    @Test
    @DisplayName("存在しないメールアドレスで検索した場合はOptional.emptyが返る")
    void testFindByEmailNotFound() {
        // when: 存在しないメールアドレスで検索
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // then: 空のOptionalが返る
        assertThat(foundUser).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("メールアドレスの存在チェックができる")
    @ValueSource(strings = {"test1@example.com", "test2@example.com", "test3@example.com"})
    void testExistsByEmail(String email) {
        // given: ユーザーを保存
        entityManager.persistAndFlush(new User("Test User", email));

        // when & then: メールアドレスの存在チェック結果が正しい
        assertThat(userRepository.existsByEmail(email)).isTrue();
        assertThat(userRepository.existsByEmail("nonexistent@example.com")).isFalse();
    }

    @Test
    @DisplayName("すべてのユーザーを取得できる")
    void testFindAll() {
        // given: 複数のユーザーを保存
        entityManager.persistAndFlush(new User("User1", "user1@example.com"));
        entityManager.persistAndFlush(new User("User2", "user2@example.com"));
        entityManager.persistAndFlush(new User("User3", "user3@example.com"));

        // when: 全ユーザーを取得
        List<User> allUsers = userRepository.findAll();

        // then: すべてのユーザーが取得できる
        assertThat(allUsers).hasSize(3).extracting(User::getName).containsExactlyInAnyOrder("User1", "User2", "User3");
    }

    @Test
    @DisplayName("ユーザーを更新できる")
    void testUpdateUser() {
        // given: ユーザーを保存
        User user = new User("Original Name", "original@example.com");
        User savedUser = entityManager.persistAndFlush(user);
        Long userId = savedUser.getId();
        LocalDateTime originalCreatedAt = savedUser.getCreatedAt();
        LocalDateTime originalUpdatedAt = savedUser.getUpdatedAt();

        // 少し待機（更新日時の違いを確実にするため）
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // when: ユーザー情報を更新
        savedUser.setName("Updated Name");
        savedUser.setEmail("updated@example.com");
        User updatedUser = userRepository.save(savedUser);
        entityManager.flush();

        // then: 更新が反映される
        assertThat(updatedUser.getId()).isEqualTo(userId);
        assertThat(updatedUser.getName()).isEqualTo("Updated Name");
        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");

        // and: 更新日時が変更される
        assertThat(updatedUser.getUpdatedAt()).isAfter(originalUpdatedAt);

        // and: 作成日時は変更されない
        assertThat(updatedUser.getCreatedAt()).isEqualTo(originalCreatedAt);
    }

    @Test
    @DisplayName("ユーザーを削除できる")
    void testDeleteUser() {
        // given: ユーザーを保存
        User user = new User("To Be Deleted", "delete@example.com");
        User savedUser = entityManager.persistAndFlush(user);
        Long userId = savedUser.getId();

        // when: ユーザーを削除
        userRepository.deleteById(userId);
        entityManager.flush();

        // then: ユーザーが削除される
        assertThat(userRepository.findById(userId)).isEmpty();
        assertThat(userRepository.existsByEmail("delete@example.com")).isFalse();
    }

    @Test
    @DisplayName("複数のユーザーを一括保存できる")
    void testSaveAll() {
        // given: 複数のユーザーを作成
        List<User> users = List.of(new User("Batch User 1", "batch1@example.com"),
                new User("Batch User 2", "batch2@example.com"), new User("Batch User 3", "batch3@example.com"));

        // when: 一括保存
        List<User> savedUsers = userRepository.saveAll(users);

        // then: すべてのユーザーが保存される
        assertThat(savedUsers).hasSize(3).allMatch(u -> u.getId() != null).allMatch(u -> u.getCreatedAt() != null);
    }

    @Test
    @DisplayName("ユーザー数をカウントできる")
    void testCount() {
        // given: 複数のユーザーを保存
        for (int i = 1; i <= 5; i++) {
            entityManager.persistAndFlush(new User("User " + i, "user" + i + "@example.com"));
        }

        // when: ユーザー数をカウント
        long count = userRepository.count();

        // then: 正しいユーザー数が返る
        assertThat(count).isEqualTo(5);
    }

    @Test
    @DisplayName("一意制約違反でエラーが発生する")
    void testUniqueConstraintViolation() {
        // given: 同じメールアドレスのユーザーを2つ作成
        User user1 = new User("User 1", "duplicate@example.com");
        User user2 = new User("User 2", "duplicate@example.com");

        // and: 1つ目を保存
        userRepository.save(user1);
        entityManager.flush();

        // when & then: 2つ目を保存しようとすると例外が発生
        assertThatThrownBy(() -> {
            userRepository.save(user2);
            entityManager.flush();
        }).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("ユーザーのtoStringメソッドが動作する")
    void testToString() {
        // given: ユーザーを作成
        User user = new User("Test User", "test@example.com");
        user.setId(1L);

        // when: toStringを呼び出し
        String userString = user.toString();

        // then: 必要な情報が含まれる
        assertThat(userString).contains("User{").contains("id=1").contains("name='Test User'")
                .contains("email='test@example.com'");
    }

    @Test
    @DisplayName("空のデータベースからfindAllを呼ぶと空のリストが返る")
    void testFindAllEmpty() {
        // when: 空のデータベースから全ユーザーを取得
        List<User> allUsers = userRepository.findAll();

        // then: 空のリストが返る
        assertThat(allUsers).isEmpty();
    }
}
