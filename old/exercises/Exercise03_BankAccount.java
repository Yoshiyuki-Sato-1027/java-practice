package old.exercises;

/**
 * 【問題3】カプセル化の練習：銀行口座クラス
 *
 * <h2>問題の目的</h2>
 * <ul>
 * <li>カプセル化の理解と実装</li>
 * <li>バリデーション付きのsetter実装</li>
 * <li>private フィールドの保護</li>
 * <li>不変オブジェクトの理解</li>
 * </ul>
 *
 * <h2>課題</h2>
 * <p>
 * 銀行口座を管理するクラスを実装してください。
 * </p>
 *
 * <h3>要件1: BankAccount クラス</h3>
 * <ul>
 * <li>private フィールド: accountNumber (String) - 口座番号（変更不可）</li>
 * <li>private フィールド: accountHolder (String) - 口座名義人</li>
 * <li>private フィールド: balance (double) - 残高</li>
 * <li>private フィールド: transactionHistory (List&lt;String&gt;) - 取引履歴</li>
 * </ul>
 *
 * <h3>要件2: コンストラクタ</h3>
 * <ul>
 * <li>BankAccount(String accountNumber, String accountHolder, double
 * initialBalance)</li>
 * <li>initialBalance は 0 以上でなければならない（バリデーション）</li>
 * <li>accountNumber は null または空文字列であってはならない</li>
 * </ul>
 *
 * <h3>要件3: メソッド</h3>
 * <ul>
 * <li>void deposit(double amount) - 入金（amountは正の数）</li>
 * <li>boolean withdraw(double amount) - 出金（残高不足ならfalseを返す）</li>
 * <li>void transfer(BankAccount to, double amount) - 振込</li>
 * <li>double getBalance() - 残高を取得</li>
 * <li>String getAccountNumber() - 口座番号を取得（setterは作らない）</li>
 * <li>String getAccountHolder() - 名義人を取得</li>
 * <li>void setAccountHolder(String name) - 名義人を変更（nullチェック）</li>
 * <li>List&lt;String&gt; getTransactionHistory() - 履歴のコピーを返す（元のリストは返さない）</li>
 * <li>void printStatement() - 口座明細を表示</li>
 * </ul>
 *
 * <h3>要件4: SavingsAccount クラス（BankAccount を継承）</h3>
 * <ul>
 * <li>追加フィールド: interestRate (double) - 金利（例: 0.02 = 2%）</li>
 * <li>void applyInterest() - 金利を適用（balance = balance * (1 + interestRate)）</li>
 * <li>最低残高: 1000円以上を維持（withdrawでチェック）</li>
 * </ul>
 *
 * <h3>要件5: バリデーションルール</h3>
 * <ul>
 * <li>入金額・出金額は正の数でなければならない</li>
 * <li>出金額が残高を超えてはならない</li>
 * <li>口座番号は変更できない</li>
 * <li>取引履歴は外部から直接変更できない</li>
 * <li>すべての取引を履歴に記録する</li>
 * </ul>
 *
 * <h2>ヒント</h2>
 * <ul>
 * <li>private フィールドを使って外部からの直接アクセスを防ぐ</li>
 * <li>getter/setter でバリデーションを実装</li>
 * <li>final キーワードで変更不可フィールドを定義</li>
 * <li>new ArrayList&lt;&gt;(transactionHistory) でリストのコピーを作成</li>
 * <li>IllegalArgumentException を使ってバリデーションエラーを通知</li>
 * </ul>
 *
 * <h2>動作確認</h2>
 *
 * <pre>
 * BankAccount account1 = new BankAccount("001", "山田太郎", 10000);
 * SavingsAccount account2 = new SavingsAccount("002", "佐藤花子", 50000, 0.02);
 *
 * account1.deposit(5000);
 * account1.withdraw(3000);
 * account1.transfer(account2, 2000);
 *
 * account2.applyInterest();
 *
 * account1.printStatement();
 * account2.printStatement();
 * </pre>
 */
public class Exercise03_BankAccount {
    public static void main(String[] args) {
        System.out.println("【問題3】銀行口座クラスの実装");
        System.out.println("この問題では、カプセル化を練習します。");
        System.out.println("\n上記のコメントを読んで、以下を実装してください：");
        System.out.println("1. BankAccount クラス");
        System.out.println("2. SavingsAccount クラス");
        System.out.println("\n実装後、このmainメソッドでテストしてください。");

        // TODO: ここに実装したクラスのテストコードを書く
    }
}

// TODO: ここから下にクラスを実装してください

/*
 * BankAccount クラスを実装
 */
// class BankAccount {
// // TODO: フィールド（private で定義）
//
// // TODO: コンストラクタ（バリデーション付き）
//
// // TODO: getter/setter メソッド
//
// // TODO: deposit, withdraw, transfer メソッド
//
// // TODO: printStatement メソッド
// }

/*
 * SavingsAccount クラスを実装
 */
// class SavingsAccount extends BankAccount {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }
