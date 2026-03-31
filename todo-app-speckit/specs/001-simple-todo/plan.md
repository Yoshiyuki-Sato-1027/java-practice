# Implementation Plan: シンプルなTodoアプリ

**Branch**: `001-simple-todo` | **Date**: 2026-03-31 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/001-simple-todo/spec.md`

## Summary

タスクの作成・完了・削除ができるシンプルなTodoWebアプリケーション。既存のSpring Boot（バックエンド）+ React/TypeScript（フロントエンド）プロジェクトをベースに、不足している機能（完了のトグル切り替え、サーバーサイドバリデーション）を追加実装する。

## Technical Context

**Language/Version**: Java 21 (backend), TypeScript 5.6 (frontend)
**Primary Dependencies**: Spring Boot 3.5.0, Doma 3.6 (ORM), React 18, Vite 5
**Storage**: MySQL 8.0 (production), H2 (testing)
**Testing**: JUnit 5 + Spring Boot Test (backend), Vitest/React Testing Library（未導入・対象外）
**Target Platform**: Webブラウザ (Chrome/Firefox/Safari 最新版)
**Project Type**: Web application (frontend + backend)
**Performance Goals**: タスク操作（作成・完了・削除）が体感的に即時反映（<500ms）
**Constraints**: 認証なし・単一ユーザー前提・永続化はDB（セッション跨ぎで保持）
**Scale/Scope**: 単一ユーザー、最大数十〜数百タスク

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

憲法ファイルはテンプレート状態（未設定）のため、プロジェクト固有のゲートなし。
代わりに一般的な品質ゲートを適用する：

| ゲート | 状態 | 備考 |
|--------|------|------|
| 既存コードとの一貫性 | PASS | 同一パッケージ構成・命名規則に従う |
| テスト実行可能性 | PASS | H2を用いたバックエンドテストが既存 |
| スコープの明確化 | PASS | 認証・永続化詳細はAssumptionsで除外済み |
| 重複実装の回避 | PASS | 既存のTodo実装を拡張（新規作成せず） |

**Post-design re-check**: Phase 1完了後、contracts/の内容が既存APIと整合しているか確認済み。✅

## Project Structure

### Documentation (this feature)

```text
specs/001-simple-todo/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/           # Phase 1 output (/speckit.plan command)
│   └── todo-api.md
└── tasks.md             # Phase 2 output (/speckit.tasks command - NOT created by /speckit.plan)
```

### Source Code (repository root)

```text
backend/
├── src/
│   ├── main/java/com/example/javapractice/
│   │   ├── entity/
│   │   │   └── Todo.java              # 既存 - 変更なし
│   │   ├── repository/
│   │   │   └── TodoDao.java           # 既存 - SQLファイルの追加が必要
│   │   ├── service/
│   │   │   └── TodoService.java       # 既存 - toggle完了メソッド追加
│   │   └── controller/
│   │       └── TodoController.java    # 既存 - toggle エンドポイント追加
│   └── test/java/com/example/javapractice/
│       └── controller/
│           └── ApiControllerTest.java # 既存 - Todo APIテスト追加
└── src/main/resources/
    └── META-INF/com/example/javapractice/repository/
        └── TodoDao/                   # Doma SQLファイル格納場所

frontend/
├── src/
│   ├── api/
│   │   └── todos.ts                   # 既存 - toggle API関数追加
│   ├── components/
│   │   ├── TodoList.tsx               # 既存 - 完了トグルUI対応
│   │   └── TodoForm.tsx               # 既存 - クライアントサイドバリデーション強化
│   └── types/
│       └── api.ts                     # 既存 - 変更なし
```

**Structure Decision**: 既存のbackend/frontendの分離構成を維持。新規ファイルは最小限とし、既存ファイルへの追記を優先する。

## Complexity Tracking

> No constitution violations found. Section left intentionally empty.
