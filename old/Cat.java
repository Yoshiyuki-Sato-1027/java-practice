package old;

/**
 * 継承の例：Cat クラス
 *
 * 【学習ポイント】
 * 1. Dog と同じく Animal を継承
 * 2. 同じ抽象メソッドを異なる方法で実装（ポリモーフィズム）
 * 3. Cat 固有の振る舞いを追加
 */
public class Cat extends Animal {
    // Cat 固有のフィールド
    private boolean isIndoor; // 室内飼いかどうか
    private int livesRemaining; // 猫の残り命（ジョーク機能）

    /**
     * コンストラクタ
     */
    public Cat(String name, int age, double weight, boolean isIndoor) {
        super(name, age, weight);
        this.isIndoor = isIndoor;
        this.livesRemaining = 9; // 猫は9つの命を持つという伝説
    }

    // getter/setter
    public boolean isIndoor() {
        return isIndoor;
    }

    public void setIndoor(boolean indoor) {
        isIndoor = indoor;
    }

    public int getLivesRemaining() {
        return livesRemaining;
    }

    /**
     * 抽象メソッドの実装：鳴き声
     * Dog とは異なる実装（ポリモーフィズム）
     */
    @Override
    public String makeSound() {
        return "ニャー！";
    }

    /**
     * 抽象メソッドの実装：移動方法
     * Dog とは異なる実装（ポリモーフィズム）
     */
    @Override
    public void move() {
        System.out.println(getName() + "は優雅に歩き、時々ジャンプします。");
    }

    /**
     * Cat 固有のメソッド
     */
    public void purr() {
        System.out.println(getName() + "はゴロゴロと喉を鳴らしています。");
    }

    /**
     * Cat 固有のメソッド
     */
    public void scratch() {
        if (isIndoor) {
            System.out.println(getName() + "は爪とぎをしています。家具が心配...");
        } else {
            System.out.println(getName() + "は木で爪を研いでいます。");
        }
    }

    /**
     * Cat 固有のメソッド
     */
    public void climbTree() {
        if (isIndoor) {
            System.out.println(getName() + "は室内飼いなので木登りはできません。");
        } else {
            System.out.println(getName() + "は器用に木に登りました！");
        }
    }

    /**
     * ジョーク機能：命を1つ失う
     */
    public void loseLife() {
        if (livesRemaining > 0) {
            livesRemaining--;
            System.out.println(getName() + "は命を1つ失いました。残り" + livesRemaining + "つ。");
        } else {
            System.out.println(getName() + "の命はもうありません...");
        }
    }

    /**
     * メソッドのオーバーライド
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("飼育環境: " + (isIndoor ? "室内" : "屋外"));
        System.out.println("残りの命: " + livesRemaining);
        System.out.println("===============");
    }

    /**
     * toString のオーバーライド
     */
    @Override
    public String toString() {
        return String.format("Cat(%s, %d歳, %.2fkg, %s飼い)",
            getName(), getAge(), getWeight(), isIndoor ? "室内" : "屋外");
    }
}
