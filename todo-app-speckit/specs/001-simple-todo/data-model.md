# Data Model: シンプルなTodoアプリ

**Branch**: `001-simple-todo` | **Date**: 2026-03-31

## エンティティ

### Todo

ユーザーが管理するタスクの単位。

| フィールド | 型 | 制約 | 説明 |
|-----------|-----|------|------|
| id | Long | PK, AUTO_INCREMENT | 一意識別子 |
| title | String | NOT NULL, NOT EMPTY | タスクのタイトル（表示名） |
| completed | boolean | NOT NULL, DEFAULT false | 完了状態 |
| createdAt | LocalDateTime | NOT NULL | 作成日時（自動セット） |
| updatedAt | LocalDateTime | NOT NULL | 最終更新日時（自動セット） |

**バリデーションルール**:
- `title`: null・空文字・空白のみは不正（400 Bad Request）
- `completed`: true（完了）/ false（未完了）のみ許容

**状態遷移**:

```
[未完了 completed=false]
        ↕ toggle操作
[完了 completed=true]
```

## DBスキーマ（MySQL）

```sql
CREATE TABLE todos (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    title      VARCHAR(255) NOT NULL,
    completed  TINYINT(1)   NOT NULL DEFAULT 0,
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL,
    PRIMARY KEY (id)
);
```

## フロントエンドの型定義（TypeScript）

```typescript
// frontend/src/types/api.ts（既存・変更なし）
interface Todo {
  id: number
  title: string
  completed: boolean
  createdAt: string
  updatedAt: string
}

interface CreateTodoRequest {
  title: string
}
```

## 注意事項

- データの永続化はMySQL（本番）/ H2（テスト）で行う
- ページリロード後もデータは保持される（Assumptionsの「セッション中のみ」は誤りで、DBで永続化済み）
- タスクの編集（titleの変更）は今バージョンのスコープ外
