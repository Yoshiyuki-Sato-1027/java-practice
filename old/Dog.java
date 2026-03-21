package old;

/**
 * 継承の例：Dog クラス
 *
 * 【学習ポイント】 1. extends キーワードで Animal を継承 2. 抽象メソッド makeSound() と move() を実装 3.
 * 親クラスのコンストラクタを super() で呼び出し 4. 独自のフィールドとメソッドを追加可能
 */
public class Dog extends Animal {
    // Dog 固有のフィールド
    private String breed; // 犬種

    /**
     * コンストラクタ super() で親クラスのコンストラクタを呼び出す
     */
    public Dog(String name, int age, double weight, String breed) {
        super(name, age, weight); // 親クラスのコンストラクタ呼び出し
        this.breed = breed;
    }

    // getter
    public String getBreed() {
        return breed;
    }

    // setter
    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * 抽象メソッドの実装：鳴き声 ポリモーフィズムの例
     */
    @Override
    public String makeSound() {
        return "ワンワン！";
    }

    /**
     * 抽象メソッドの実装：移動方法 ポリモーフィズムの例
     */
    @Override
    public void move() {
        System.out.println(getName() + "は四足歩行で駆け回ります！");
    }

    /**
     * Dog 固有のメソッド
     */
    public void fetch() {
        System.out.println(getName() + "はボールを取ってきました！いい子だ！");
    }

    /**
     * Dog 固有のメソッド
     */
    public void wagTail() {
        System.out.println(getName() + "は嬉しそうに尻尾を振っています！");
    }

    /**
     * メソッドのオーバーライド例 親クラスの displayInfo() をオーバーライドして犬種情報を追加
     */
    @Override
    public void displayInfo() {
        super.displayInfo(); // 親クラスのメソッドを呼び出し
        System.out.println("犬種: " + breed);
        System.out.println("===============");
    }

    /**
     * toString のオーバーライド
     */
    @Override
    public String toString() {
        return String.format("Dog(%s, %s, %d歳, %.2fkg)", getName(), breed, getAge(), getWeight());
    }
}
