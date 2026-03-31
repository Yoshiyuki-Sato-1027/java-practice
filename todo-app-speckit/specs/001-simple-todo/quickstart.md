# Quickstart: シンプルなTodoアプリ

**Branch**: `001-simple-todo` | **Date**: 2026-03-31

## 前提条件

- Java 21+
- Node.js 18+
- Docker & Docker Compose

## 起動手順

### 1. データベースを起動

```bash
cd /path/to/java-practice
docker compose up -d
```

MySQLが `localhost:3306` で起動する。

### 2. バックエンドを起動

```bash
cd backend
./gradlew bootRun
```

`http://localhost:8080` でAPIが起動する。

### 3. フロントエンドを起動

```bash
cd frontend
npm install
npm run dev
```

`http://localhost:5173` でUIが起動する。

---

## 動作確認

### UIで確認

ブラウザで `http://localhost:5173` を開き、「Todo API」セクションで操作できる。

- **追加**: タイトルを入力して「Todo追加」ボタンをクリック
- **完了**: タスク行の「完了」ボタンをクリック（完了済みは「未完了に戻す」ボタンが表示）
- **削除**: タスク行の「削除」ボタンをクリック

### APIで確認

```bash
# タスク一覧取得
curl http://localhost:8080/api/todos

# タスク作成
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "サンプルタスク"}'

# 完了状態のトグル（ID=1）
curl -X PUT http://localhost:8080/api/todos/1/toggle

# タスク削除（ID=1）
curl -X DELETE http://localhost:8080/api/todos/1
```

---

## テスト実行

```bash
cd backend
./gradlew test
```

テスト結果はコンソールに出力される。H2インメモリDBを使用するため、MySQLの起動は不要。

---

## コードフォーマット

```bash
cd backend
./gradlew spotlessApply
```

---

## トラブルシューティング

| 問題 | 対処 |
|------|------|
| DBに接続できない | `docker compose ps` でMySQLコンテナの状態を確認 |
| ポート競合 | `docker compose down` して再起動 |
| フロントがAPIに繋がらない | バックエンドが起動しているか、CORSConfig.javaの設定を確認 |
