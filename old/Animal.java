package old;

/**
 * 抽象クラスの例：Animal
 *
 * 【学習ポイント】
 * 1. 抽象クラスは abstract キーワードで定義
 * 2. 抽象メソッド（実装を持たない）を定義可能
 * 3. 具象メソッド（実装を持つ）も定義可能
 * 4. インスタンス化できない（new Animal() は不可）
 * 5. カプセル化：private フィールド + getter/setter
 */
public abstract class Animal {
    // カプセル化の例：private フィールド
    private String name;
    private int age;
    private double weight; // kg単位

    /**
     * コンストラクタ
     * 抽象クラスでもコンストラクタを持てる（サブクラスから呼ばれる）
     */
    public Animal(String name, int age, double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    // カプセル化：getter メソッド
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    // カプセル化：setter メソッド（バリデーション付き）
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("名前は空にできません");
        }
        this.name = name;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("年齢は0以上である必要があります");
        }
        this.age = age;
    }

    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("体重は正の数である必要があります");
        }
        this.weight = weight;
    }

    /**
     * 抽象メソッドの例
     * サブクラスで必ず実装する必要がある
     */
    public abstract String makeSound();

    /**
     * 抽象メソッドの例2
     * それぞれの動物固有の行動
     */
    public abstract void move();

    /**
     * 具象メソッドの例
     * すべての動物に共通の振る舞い
     */
    public void sleep() {
        System.out.println(name + "は眠っています... Zzz");
    }

    /**
     * 具象メソッド：情報表示
     */
    public void displayInfo() {
        System.out.println("=== 動物情報 ===");
        System.out.println("名前: " + name);
        System.out.println("年齢: " + age + "歳");
        System.out.println("体重: " + weight + "kg");
        System.out.println("鳴き声: " + makeSound());
    }

    /**
     * 年を取るメソッド
     */
    public void haveBirthday() {
        age++;
        System.out.println(name + "の誕生日！ " + age + "歳になりました！");
    }

    /**
     * 食事をするメソッド
     */
    public void eat(double foodWeight) {
        if (foodWeight > 0) {
            weight += foodWeight * 0.1; // 食べた量の10%が体重増加
            System.out.println(name + "は" + foodWeight + "kgの食事をしました。");
            System.out.printf("現在の体重: %.2fkg\n", weight);
        }
    }

    /**
     * toString のオーバーライド
     */
    @Override
    public String toString() {
        return String.format("%s(%s, %d歳, %.2fkg)",
            getClass().getSimpleName(), name, age, weight);
    }
}
