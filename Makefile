.PHONY: up down backend frontend install test format

# MySQLをDockerで起動
up:
	docker-compose up -d

# MySQLを停止
down:
	docker-compose down

# Spring Bootバックエンドを起動
backend:
	cd backend && ./gradlew bootRun

# Viteフロントエンド開発サーバーを起動
frontend:
	cd frontend && npm run dev

# フロントエンド依存関係インストール
install:
	cd frontend && npm install

# バックエンドテスト実行
test:
	cd backend && ./gradlew test

# Spotlessでコードフォーマット
format:
	cd backend && ./gradlew spotlessApply

# 通常の起動方法:
# ターミナル1: make up && make backend
# ターミナル2: make frontend
# ブラウザ:    http://localhost:5173
