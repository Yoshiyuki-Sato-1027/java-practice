# Research: シンプルなTodoアプリ

**Branch**: `001-simple-todo` | **Date**: 2026-03-31

## 現状分析

### 既存実装の状況

既存のコードベースにはすでにTodo機能の大部分が実装されている。

| 機能 | バックエンド | フロントエンド | 仕様との差分 |
|------|------------|--------------|------------|
| タスク一覧取得 | ✅ GET /api/todos | ✅ fetchTodos() | なし |
| タスク作成 | ✅ POST /api/todos | ✅ TodoForm.tsx | サーバーサイドバリデーション未実装 |
| タスク完了（完了にする） | ✅ PUT /api/todos/{id}/complete | ✅ completeTodo() | 完了→未完了の戻しが未実装 |
| タスク削除 | ✅ DELETE /api/todos/{id} | ✅ deleteTodo() | なし |
| **完了トグル（未完了に戻す）** | ❌ 未実装 | ❌ 未実装 | **FR-005違反** |
| **空タイトルのサーバーサイド拒否** | ❌ 未実装 | △ required属性のみ | **FR-002違反** |

### 実装ギャップの詳細

#### 1. 完了トグル（FR-005）

- **現状**: `PUT /api/todos/{id}/complete` は常に `completed = true` にセットする
- **必要**: 完了状態を切り替えられる（true → false、false → true）
- **実装方針**: 既存エンドポイントを汎用的な `PUT /api/todos/{id}/toggle` に変更するか、PATCHリクエストで任意の状態をセットする

#### 2. サーバーサイドバリデーション（FR-002）

- **現状**: フロントエンドの `required` 属性のみ。空文字をPOSTすれば通過する
- **必要**: タイトルが空または空白のみの場合は400 Bad Requestを返す
- **実装方針**: Service層またはController層でバリデーション

---

## 技術的決定事項

### Decision 1: 完了トグルのAPIデザイン

- **Decision**: `PUT /api/todos/{id}/complete` を `PUT /api/todos/{id}/toggle` にリネーム・変更し、現在の完了状態を反転する
- **Rationale**: 仕様のFR-004/FR-005が「完了/未完了の切り替え」を求めている。PATCH + body（`{"completed": true/false}`）も選択肢だが、シンプルなtoggleエンドポイントの方がクライアントコードが簡潔
- **Alternatives considered**:
  - PATCH `/api/todos/{id}` でbody `{"completed": bool}`: より汎用的だが、現時点でtitle編集はスコープ外のため過剰
  - 別エンドポイント（/complete と /uncomplete）: APIが増えて管理が煩雑

### Decision 2: バリデーション実装場所

- **Decision**: `TodoService.create()` 内でバリデーションを実施し、不正入力時は `IllegalArgumentException` をスローし、`@ControllerAdvice` で400に変換
- **Rationale**: ビジネスルール（空タイトル禁止）はサービス層の責務。Controller層では薄くする
- **Alternatives considered**:
  - Bean Validation (`@NotBlank`): Spring Boot標準だが、DomaエンティティへのJakarta Validationアノテーション追加が必要で若干複雑
  - Controller層での手動チェック: サービスが薄くなりすぎる

### Decision 3: フロントエンドの完了トグルUI

- **Decision**: 完了済みタスクに「未完了に戻す」ボタンを追加（既存の完了ボタンと対称配置）
- **Rationale**: シンプルで分かりやすい。チェックボックス形式も検討したが、既存テーブルレイアウトとの整合性を考慮
- **Alternatives considered**:
  - チェックボックスでトグル: よりモダンだが既存UIのリファクタリングが必要（スコープ外）

---

## 結論：実装タスクの要約

1. **バックエンド**: `TodoService.update()` をtoggle対応に変更 + バリデーション追加
2. **バックエンド**: `TodoController` の `/complete` エンドポイントを `/toggle` にリネーム
3. **フロントエンド**: `todos.ts` の `completeTodo` を `toggleTodo` に変更
4. **フロントエンド**: `TodoList.tsx` に「未完了に戻す」ボタンを追加、バリデーションメッセージ改善
5. **テスト**: バックエンドのtoggle・バリデーションのユニット/統合テスト追加
