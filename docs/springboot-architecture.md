# Spring Boot プロジェクト構成ガイド

## プロジェクト構成と処理フロー

```
リクエスト → Controller → Service → Repository → Database
              ↓
         レスポンス
```

---

## 1. JavaPracticeApplication.java (エントリーポイント)

**場所:** `src/main/java/com/example/javapractice/JavaPracticeApplication.java:7-11`

### 役割
Spring Bootアプリケーションの起動クラス

### 処理内容
```java
@SpringBootApplication  // 3つのアノテーションの組み合わせ
public class JavaPracticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaPracticeApplication.class, args);
    }
}
```

- `@SpringBootApplication` は以下を含む:
  - `@Configuration` - Bean定義
  - `@EnableAutoConfiguration` - 自動設定
  - `@ComponentScan` - コンポーネントスキャン

**起動時の処理:**
1. Tomcatサーバー起動
2. application.propertiesを読み込み
3. Bean登録（Controller、Repository等）
4. データベース接続確立

---

## 2. application.properties (設定ファイル)

**場所:** `src/main/resources/application.properties:1-14`

### 役割
アプリケーション全体の設定を管理

### 主要設定

**サーバー設定:**
```properties
server.port=8080  # アプリが起動するポート番号
```

**データベース接続:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/practice_db
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

**JPA/Hibernate設定:**
```properties
spring.jpa.hibernate.ddl-auto=update    # テーブル自動作成/更新
spring.jpa.show-sql=true                # SQL文をコンソール表示
spring.jpa.properties.hibernate.format_sql=true  # SQL整形
```

`ddl-auto=update` の動作:
- エンティティクラスからテーブル自動生成
- 既存テーブルは削除せず、カラム追加のみ

---

## 3. User.java (エンティティ)

**場所:** `src/main/java/com/example/javapractice/entity/User.java:7-99`

### 役割
データベーステーブル「users」とJavaオブジェクトをマッピング

### 主要アノテーション

```java
@Entity                              // JPA管理対象
@Table(name = "users")              // テーブル名指定
public class User {

    @Id                             // 主キー
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 自動採番
    private Long id;

    @Column(nullable = false, length = 50)  // カラム制約
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @PrePersist   // 登録前に実行
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate    // 更新前に実行
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

**対応するテーブル定義:**
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
```

---

## 4. UserRepository.java (リポジトリ)

**場所:** `src/main/java/com/example/javapractice/repository/UserRepository.java:9-15`

### 役割
データベース操作（CRUD）を提供

### 処理内容

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);  // メールで検索

    boolean existsByEmail(String email);       // メール存在チェック
}
```

**JpaRepositoryから継承される主要メソッド:**
- `save(User user)` - 保存/更新
- `findById(Long id)` - ID検索
- `findAll()` - 全件取得
- `deleteById(Long id)` - 削除
- `count()` - 件数取得

**メソッド名から自動生成されるSQL:**
```java
findByEmail("test@example.com")
→ SELECT * FROM users WHERE email = 'test@example.com';

existsByEmail("test@example.com")
→ SELECT COUNT(*) > 0 FROM users WHERE email = 'test@example.com';
```

---

## 5. ApiController.java (コントローラー)

**場所:** `src/main/java/com/example/javapractice/controller/ApiController.java:13-63`

### 役割
HTTPリクエストを受け取り、レスポンスを返す

### エンドポイント一覧

**1. `/api/hello`** (17-23行目)
```java
@GetMapping("/hello")
public Map<String, String> hello() {
    return {"message": "Hello, World!", "status": "success"};
}
```
- HTTP GET http://localhost:8080/api/hello
- 固定メッセージを返す

**2. `/api/greet?name=太郎`** (25-31行目)
```java
@GetMapping("/greet")
public Map<String, String> greet(
    @RequestParam(defaultValue = "Guest") String name
) {
    return {"message": "Hello, 太郎!", "status": "success"};
}
```
- クエリパラメータを受け取る
- デフォルト値: "Guest"

**3. `/api/time`** (33-43行目)
```java
@GetMapping("/time")
public Map<String, String> time() {
    LocalDateTime now = LocalDateTime.now();
    return {
        "timestamp": "2026-03-14T15:30:00",
        "date": "2026-03-14",
        "time": "15:30:00"
    };
}
```
- 現在日時を返す

**4. `/api/status`** (45-61行目)
```java
@GetMapping("/status")
public Map<String, Object> status() {
    // メモリ使用状況をMB単位で返す
    return {
        "status": "running",
        "memory": {
            "total": 512,
            "used": 256,
            "free": 256
        }
    };
}
```
- JVMメモリ状態を返す

---

## 実際のリクエスト処理フロー

### 例: ユーザー作成（仮のServiceがある場合）

```
1. ブラウザ/クライアント
   POST /api/users
   Body: {"name": "太郎", "email": "taro@example.com"}

2. ApiController
   @PostMapping("/users")
   public User createUser(@RequestBody User user) {
       return userService.create(user);  // Serviceへ
   }

3. UserService（現在未作成）
   public User create(User user) {
       if (userRepository.existsByEmail(user.getEmail())) {
           throw new Exception("メール重複");
       }
       return userRepository.save(user);  // Repositoryへ
   }

4. UserRepository
   save(user)
   → SQL: INSERT INTO users (name, email, created_at, updated_at)
          VALUES ('太郎', 'taro@example.com', NOW(), NOW());

5. Database
   レコード保存

6. レスポンス
   {"id": 1, "name": "太郎", "email": "taro@example.com", ...}
```

---

## 現在のプロジェクトの特徴

### 存在するもの
- Controller（APIエンドポイント）
- Entity（データモデル）
- Repository（DB操作）
- 設定ファイル

### まだないもの
- Service層（ビジネスロジック）
- UserControllerでのCRUD実装
- エラーハンドリング
- バリデーション

**注意:** UserRepositoryは定義されていますが、Controllerから実際に使われていない状態です。実際にユーザーCRUDを実装する場合は、UserControllerとUserServiceを追加することをおすすめします。

---

## Spring Boot 主要アノテーション一覧

### クラスレベル
- `@SpringBootApplication` - アプリケーションのエントリーポイント
- `@RestController` - RESTful APIコントローラー
- `@Service` - ビジネスロジック層
- `@Repository` - データアクセス層
- `@Entity` - JPA エンティティ
- `@Configuration` - 設定クラス

### メソッドレベル
- `@GetMapping` - HTTP GET リクエスト
- `@PostMapping` - HTTP POST リクエスト
- `@PutMapping` - HTTP PUT リクエスト
- `@DeleteMapping` - HTTP DELETE リクエスト
- `@RequestParam` - クエリパラメータ
- `@PathVariable` - パスパラメータ
- `@RequestBody` - リクエストボディ

### フィールドレベル
- `@Autowired` - 依存性注入
- `@Id` - 主キー
- `@GeneratedValue` - 自動採番
- `@Column` - カラム定義

---

生成日: 2026-03-14
バージョン: Java 25, Spring Boot 3.5.0
