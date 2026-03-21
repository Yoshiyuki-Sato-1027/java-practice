package old.exercises;

/**
 * 【問題4】総合演習：従業員管理システム
 *
 * <h2>問題の目的</h2>
 * <ul>
 * <li>これまで学んだすべての概念の統合</li>
 * <li>継承、ポリモーフィズム、インターフェース、カプセル化の実践</li>
 * <li>複雑なクラス設計の練習</li>
 * </ul>
 *
 * <h2>課題</h2>
 * <p>
 * 会社の従業員管理システムを実装してください。
 * </p>
 *
 * <h3>要件1: Payable インターフェース</h3>
 * <ul>
 * <li>メソッド: double calculateSalary() - 給与を計算</li>
 * </ul>
 *
 * <h3>要件2: Employee 抽象クラス（Payable を実装）</h3>
 * <ul>
 * <li>private フィールド: employeeId (String), name (String), joinDate (String)</li>
 * <li>コンストラクタ: 3つのフィールドを初期化</li>
 * <li>抽象メソッド: String getEmployeeType() - 従業員タイプを返す</li>
 * <li>抽象メソッド: double calculateSalary() - 給与計算（Payable の実装）</li>
 * <li>具象メソッド: void displayInfo() - 従業員情報を表示</li>
 * <li>適切な getter/setter</li>
 * </ul>
 *
 * <h3>要件3: FullTimeEmployee クラス（Employee を継承）</h3>
 * <ul>
 * <li>private フィールド: monthlySalary (double) - 月給</li>
 * <li>private フィールド: bonus (double) - ボーナス</li>
 * <li>calculateSalary() を実装: monthlySalary + bonus</li>
 * <li>getEmployeeType() を実装: "正社員" を返す</li>
 * </ul>
 *
 * <h3>要件4: PartTimeEmployee クラス（Employee を継承）</h3>
 * <ul>
 * <li>private フィールド: hourlyRate (double) - 時給</li>
 * <li>private フィールド: hoursWorked (double) - 労働時間</li>
 * <li>calculateSalary() を実装: hourlyRate * hoursWorked</li>
 * <li>getEmployeeType() を実装: "アルバイト" を返す</li>
 * <li>void addWorkHours(double hours) - 労働時間を追加</li>
 * </ul>
 *
 * <h3>要件5: Manager クラス（FullTimeEmployee を継承）</h3>
 * <ul>
 * <li>private フィールド: departmentBonus (double) - 部門ボーナス</li>
 * <li>private フィールド: teamMembers (List&lt;Employee&gt;) - 部下のリスト</li>
 * <li>calculateSalary() をオーバーライド: 親のsalary + departmentBonus</li>
 * <li>getEmployeeType() をオーバーライド: "マネージャー" を返す</li>
 * <li>void addTeamMember(Employee e) - 部下を追加</li>
 * <li>double getTeamTotalSalary() - チーム全体の給与総額を計算</li>
 * </ul>
 *
 * <h3>要件6: Company クラス</h3>
 * <ul>
 * <li>private フィールド: companyName (String)</li>
 * <li>private フィールド: employees (List&lt;Employee&gt;)</li>
 * <li>void hireEmployee(Employee e) - 従業員を雇用</li>
 * <li>boolean fireEmployee(String employeeId) - 従業員を解雇</li>
 * <li>Employee findEmployee(String employeeId) - IDで従業員を検索</li>
 * <li>List&lt;Employee&gt; getEmployeesByType(String type) - タイプで従業員をフィルタ</li>
 * <li>double getTotalPayroll() - 全従業員の給与総額</li>
 * <li>void displayAllEmployees() - 全従業員の情報を表示</li>
 * <li>void processMonthlyPayroll() - 月次給与処理（全員の給与を表示）</li>
 * </ul>
 *
 * <h3>要件7: バリデーション</h3>
 * <ul>
 * <li>給与、時給、労働時間は正の数</li>
 * <li>従業員IDは重複しない</li>
 * <li>null チェックを適切に実装</li>
 * </ul>
 *
 * <h2>ヒント</h2>
 * <ul>
 * <li>すべてのOOP概念を活用する</li>
 * <li>super キーワードで親クラスのメソッドを呼び出す</li>
 * <li>instanceof で従業員のタイプをチェック</li>
 * <li>Stream API を使うと getEmployeesByType が簡潔に書ける（任意）</li>
 * </ul>
 *
 * <h2>動作確認</h2>
 *
 * <pre>
 * Company company = new Company("株式会社サンプル");
 *
 * FullTimeEmployee ft1 = new FullTimeEmployee("E001", "山田太郎", "2020-04-01", 300000, 50000);
 * PartTimeEmployee pt1 = new PartTimeEmployee("E002", "佐藤花子", "2023-01-15", 1500, 80);
 * Manager manager = new Manager("M001", "田中一郎", "2015-04-01", 500000, 100000, 200000);
 *
 * manager.addTeamMember(ft1);
 * manager.addTeamMember(pt1);
 *
 * company.hireEmployee(manager);
 * company.hireEmployee(ft1);
 * company.hireEmployee(pt1);
 *
 * company.displayAllEmployees();
 * System.out.println("総人件費: " + company.getTotalPayroll() + "円");
 * company.processMonthlyPayroll();
 * </pre>
 */
public class Exercise04_Employee {
    public static void main(String[] args) {
        System.out.println("【問題4】従業員管理システムの実装");
        System.out.println("この問題では、すべてのOOP概念を統合して実践します。");
        System.out.println("\n上記のコメントを読んで、以下を実装してください：");
        System.out.println("1. Payable インターフェース");
        System.out.println("2. Employee 抽象クラス");
        System.out.println("3. FullTimeEmployee, PartTimeEmployee クラス");
        System.out.println("4. Manager クラス");
        System.out.println("5. Company クラス");
        System.out.println("\n実装後、このmainメソッドでテストしてください。");

        // TODO: ここに実装したクラスのテストコードを書く
    }
}

// TODO: ここから下にインターフェースとクラスを実装してください

/*
 * Payable インターフェースを実装
 */
// interface Payable {
// // TODO: メソッドを定義
// }

/*
 * Employee 抽象クラスを実装
 */
// abstract class Employee implements Payable {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }

/*
 * FullTimeEmployee クラスを実装
 */
// class FullTimeEmployee extends Employee {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }

/*
 * PartTimeEmployee クラスを実装
 */
// class PartTimeEmployee extends Employee {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }

/*
 * Manager クラスを実装
 */
// class Manager extends FullTimeEmployee {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }

/*
 * Company クラスを実装
 */
// class Company {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }
