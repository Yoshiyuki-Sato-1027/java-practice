# Simple Web API

Java標準のHttpServerを使用したシンプルなREST APIです。

## 実行方法

```bash
# コンパイル
javac src/SimpleWebAPI.java

# 実行
java -cp src SimpleWebAPI
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
{"timestamp": "2026-02-07T18:30:00", "date": "2026-02-07", "time": "18:30:00"}
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

- ✅ JSONレスポンス
- ✅ クエリパラメータのパース
- ✅ HTTPメソッドの検証
- ✅ CORS対応
- ✅ エラーハンドリング

## 停止方法

Ctrl+C でサーバーを停止できます。
