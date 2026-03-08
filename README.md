# Spring Boot REST API

Spring Bootを使用したシンプルなREST APIです。

## 技術スタック

- Java 17
- Spring Boot 3.2.3
- Gradle

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
- Java 17以上
- Gradle（またはGradleラッパー）

初回実行時、IntelliJ IDEAがGradleラッパー（`gradlew`）を自動生成します。
