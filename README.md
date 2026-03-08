# Spring Boot REST API

Spring Bootを使用したシンプルなREST APIです。

## 技術スタック

- Java 21
- Spring Boot 3.4.2
- Gradle
- MySQL 8.0
- Spring Data JPA

## プロジェクト構成

```
src/
├── main/
│   ├── java/
│   │   └── com/example/javapractice/
│   │       ├── JavaPracticeApplication.java    # メインアプリケーション
│   │       └── controller/
│   │           └── ApiController.java          # REST APIコントローラー
│   └── resources/
│       └── application.properties              # 設定ファイル
└── test/
    └── java/
```

## MySQL環境構築

このアプリケーションはMySQLデータベースを使用します。以下の手順でMySQLサーバーを起動してください。

### Docker Composeを使用する場合（推奨）

プロジェクトルートに`docker-compose.yml`が用意されています。

```bash
# MySQLコンテナを起動
docker-compose up -d

# コンテナの状態を確認
docker-compose ps

# ログを確認
docker-compose logs mysql

# 停止する場合
docker-compose down
```

### 既存のDockerコンテナを使用する場合

既にMySQLコンテナが起動している場合は、そのコンテナを利用できます。

```bash
# コンテナを確認
docker ps | grep mysql

# データベースを作成
docker exec <container-name> mysql -u root -p<password> -e "CREATE DATABASE IF NOT EXISTS practice_db;"

# データベース一覧を確認
docker exec <container-name> mysql -u root -p<password> -e "SHOW DATABASES;"
```

### 接続設定

`src/main/resources/application.properties`に以下の設定があります:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/practice_db
spring.datasource.username=root
spring.datasource.password=mypassword
```

環境に合わせて接続情報を変更してください。

## 実行方法

### IntelliJ IDEAで実行

1. IntelliJ IDEAでプロジェクトを開く
2. Gradleプロジェクトとして同期
3. `JavaPracticeApplication.java` を右クリックして「Run」

### Gradleコマンドで実行

```bash
# Gradleラッパーを使用（推奨）
./gradlew bootRun

# または、ビルドしてから実行
./gradlew build
java -jar build/libs/java-practice-0.0.1-SNAPSHOT.jar
```

サーバーはポート8080で起動します。

## APIエンドポイント

### 1. Hello World

```bash
curl http://localhost:8080/api/hello
```

**レスポンス:**
```json
{"message": "Hello, World!", "status": "success"}
```

### 2. 挨拶（パラメータ付き）

```bash
curl "http://localhost:8080/api/greet?name=Taro"
```

**レスポンス:**
```json
{"message": "Hello, Taro!", "status": "success"}
```

### 3. 現在時刻

```bash
curl http://localhost:8080/api/time
```

**レスポンス:**
```json
{"timestamp": "2026-03-08T14:30:00", "date": "2026-03-08", "time": "14:30:00"}
```

### 4. サーバーステータス

```bash
curl http://localhost:8080/api/status
```

**レスポンス:**
```json
{"status": "running", "memory": {"total": 256, "used": 128, "free": 128}}
```

## 機能

- ✅ Spring Boot REST API
- ✅ JSONレスポンス（自動シリアライズ）
- ✅ リクエストパラメータのバインディング
- ✅ 組み込みTomcatサーバー
- ✅ CORS対応（Spring Boot自動設定）
- ✅ MySQL データベース連携
- ✅ Spring Data JPA（Hibernate）
- ✅ 自動テーブル生成（ddl-auto=update）

## 開発

### ビルド

```bash
./gradlew build
```

### テスト実行

```bash
./gradlew test
```

### クリーン＆ビルド

```bash
./gradlew clean build
```

## 停止方法

- IntelliJ IDEA: 停止ボタンをクリック
- コマンドライン: `Ctrl+C`

## 注意事項

このプロジェクトを実行するには以下が必要です：
- Java 21以上
- Gradle（またはGradleラッパー）
- Docker（MySQL環境用）
- MySQL 8.0（Dockerコンテナで起動）

初回実行時、IntelliJ IDEAがGradleラッパー（`gradlew`）を自動生成します。

**重要**: アプリケーション起動前にMySQLサーバーが起動していることを確認してください。
