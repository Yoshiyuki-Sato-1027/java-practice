# Tasks: シンプルなTodoアプリ

**Input**: Design documents from `/specs/001-simple-todo/`
**Prerequisites**: plan.md ✅, spec.md ✅, research.md ✅, data-model.md ✅, contracts/ ✅

**Organization**: 既存コードに2つのギャップを特定済み（research.md参照）。タスクはユーザーストーリー単位で整理し、独立してテスト・デモ可能な単位で実装する。

## Format: `[ID] [P?] [Story] Description`

- **[P]**: 並列実行可能（異なるファイル、依存関係なし）
- **[Story]**: 対応するユーザーストーリー（US1/US2/US3）

## Path Conventions

- バックエンド: `backend/src/main/java/com/example/javapractice/`
- フロントエンド: `frontend/src/`

---

## Phase 1: Setup（既存構成の確認）

**Purpose**: 既存プロジェクト構成を確認し、実装に必要なSQL/設定ファイルの有無を把握する

- [x] T001 既存のTodo関連ファイル一覧を確認する（`backend/src/main/resources/META-INF/` 配下のDomaのSQLファイルの存在確認）
- [x] T002 `docker compose up -d` でMySQLを起動し、`todos` テーブルが存在することを確認する

---

## Phase 2: Foundational（エラーハンドリング基盤）

**Purpose**: バリデーションエラーをHTTP 400で返すための共通エラーハンドリングを実装する（全ユーザーストーリーの前提）

**⚠️ CRITICAL**: このフェーズ完了後にユーザーストーリーの実装を開始する

- [x] T003 `GlobalExceptionHandler.java` を作成し `@ControllerAdvice` でバリデーション例外（`IllegalArgumentException`）を 400 Bad Request にマップする（`backend/src/main/java/com/example/javapractice/controller/GlobalExceptionHandler.java`）

**Checkpoint**: GlobalExceptionHandler が動作すること → `curl -X POST http://localhost:8080/api/todos -d '{}'` で400が返ることで確認

---

## Phase 3: User Story 1 - タスクの作成（Priority: P1）🎯 MVP

**Goal**: ユーザーが空でないタスク名を入力してTodoを作成でき、空入力は拒否される

**Independent Test**: タスク一覧が空の状態で「買い物する」を追加 → リストに表示される。空文字を送信 → エラーが表示される。この2操作のみで独立してテスト可能。

### Implementation for User Story 1

- [x] T004 [US1] `TodoService.create()` にバリデーションを追加する：titleがnull・空文字・空白のみの場合は `IllegalArgumentException` をスロー（`backend/src/main/java/com/example/javapractice/service/TodoService.java`）
- [x] T005 [P] [US1] `TodoControllerTest.java` にバリデーションの統合テストを追加する：空タイトルで400が返ることを確認（`backend/src/test/java/com/example/javapractice/controller/TodoControllerTest.java`）
- [x] T006 [P] [US1] `TodoForm.tsx` のエラー表示を改善する：HTMLの `required` に加え、空白のみの入力も防ぐクライアントサイドチェックを追加（`frontend/src/components/TodoForm.tsx`）

**Checkpoint**: `curl -X POST .../todos -d '{"title":""}' → 400`、UIで空のまま送信 → エラー表示、有効なタイトルで追加 → リスト更新

---

## Phase 4: User Story 2 - タスクの完了（Priority: P2）

**Goal**: ユーザーが完了済みタスクを未完了に戻せる（完了状態をトグルできる）

**Independent Test**: タスクを作成 → 「完了」ボタンで完了にする → 「未完了に戻す」ボタンが表示される → クリックで未完了に戻る。この一連の流れのみで独立してテスト可能。

### Implementation for User Story 2

- [x] T007 [US2] `TodoService.update()` をtoggle対応に変更する：引数 `boolean completed` を削除し、現在の状態を反転させるロジックに書き換える（`backend/src/main/java/com/example/javapractice/service/TodoService.java`）
- [x] T008 [US2] `TodoController` の `/complete` エンドポイントを `/toggle` にリネームし、`todoService.update(id)` の呼び出しを更新する（`backend/src/main/java/com/example/javapractice/controller/TodoController.java`）
- [x] T009 [P] [US2] `TodoControllerTest.java` にトグルの統合テストを追加する：false→true→false の往復を確認（`backend/src/test/java/com/example/javapractice/controller/TodoControllerTest.java`）
- [x] T010 [P] [US2] `todos.ts` の `completeTodo` 関数を `toggleTodo` にリネームし、エンドポイントを `/toggle` に変更する（`frontend/src/api/todos.ts`）
- [x] T011 [US2] `TodoList.tsx` を更新する：完了済みタスクに「未完了に戻す」ボタンを追加し、`completeTodo` を `toggleTodo` に置き換える（`frontend/src/components/TodoList.tsx`）

**Checkpoint**: UIで完了ボタン押下 → 完了表示、「未完了に戻す」ボタン押下 → 未完了表示に戻る

---

## Phase 5: User Story 3 - タスクの削除（Priority: P3）

**Goal**: ユーザーが任意のタスクをリストから削除できる（既に実装済み・動作確認のみ）

**Independent Test**: タスクを作成 → 「削除」ボタンをクリック → リストから消える。この操作のみで独立してテスト可能。

### Implementation for User Story 3

- [x] T012 [US3] 削除機能の動作確認：`curl -X DELETE http://localhost:8080/api/todos/{id}` で204が返り、UIで削除ボタンが正常に動作することを確認する
- [x] T013 [P] [US3] `TodoControllerTest.java` に削除の統合テストを追加する：存在するIDの削除で204、削除後のGETでリストから消えることを確認（`backend/src/test/java/com/example/javapractice/controller/TodoControllerTest.java`）

**Checkpoint**: 全3ストーリーが独立して動作確認済み

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: 複数ストーリーに跨る改善・最終確認

- [ ] T014 [P] `quickstart.md` の手順に沿って環境を起動し、全機能（作成・完了トグル・削除）をエンドツーエンドで動作確認する（`specs/001-simple-todo/quickstart.md`）
- [ ] T015 [P] `./gradlew spotlessApply` を実行してバックエンドコードのフォーマットを整える（`backend/`） ⚠️ Java 21環境が必要
- [ ] T016 `./gradlew test` で全テストが通ることを確認する（`backend/`） ⚠️ Java 21環境が必要

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: 依存なし - 即時開始可能
- **Foundational (Phase 2)**: Phase 1完了後 - 全ユーザーストーリーをブロック
- **User Stories (Phase 3/4/5)**: Phase 2完了後に開始可能
  - US1とUS2/US3は独立して並列実施可能（ただしUS2とUS3はUS1の作成機能でデータを用意するため、順番に実施推奨）
- **Polish (Phase 6)**: 全ユーザーストーリー完了後

### User Story Dependencies

- **US1 (P1)**: Foundational完了後に開始可能 - 他ストーリーへの依存なし
- **US2 (P2)**: Foundational完了後に開始可能 - US1の `TodoService` と `TodoController` を修正するため US1 完了後推奨
- **US3 (P3)**: すでに実装済みのため確認のみ - US1/US2と並列実施可能

### Parallel Opportunities

- T005と T006はそれぞれバックエンドとフロントエンドのため並列実施可能
- T009とT010はそれぞれテストとAPIクライアントのため並列実施可能
- T013と T014は独立タスクのため並列実施可能

---

## Parallel Example: User Story 2

```bash
# US2の並列実施可能なタスク
Task T009: TodoControllerTest.java にtoggleのテスト追加（バックエンド）
Task T010: todos.ts の toggleTodo 実装（フロントエンド）

# これらはT007/T008完了後に並列開始可能
```

---

## Implementation Strategy

### MVP First（User Story 1のみ）

1. Phase 1: Setup（確認）
2. Phase 2: Foundational（GlobalExceptionHandler実装）
3. Phase 3: US1（バリデーション追加）
4. **STOP & VALIDATE**: タスク作成と空バリデーションを独立テスト
5. デモ可能

### Incremental Delivery

1. Setup + Foundational → 基盤完成
2. US1追加 → 作成バリデーション確認 → デモ（MVP！）
3. US2追加 → 完了トグル確認 → デモ
4. US3確認 → 削除動作確認 → デモ
5. Polish → 全機能最終確認

---

## Notes

- [P] タスク = 異なるファイル、依存なし
- [Story] ラベルは spec.md のユーザーストーリーとの対応
- US3はすでに実装済みのため T012 は動作確認のみ（新規コードなし）
- `TodoControllerTest.java` は新規作成が必要な可能性あり（現在は `ApiControllerTest.java` のみ存在）
- Domaのtoggle実装では `todoDao.selectById()` で現在の状態を取得してから反転させる
