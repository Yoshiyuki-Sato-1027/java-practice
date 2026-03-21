package old;

/**
 * オブジェクト指向プログラミング（OOP）のデモンストレーション
 *
 * このクラスは以下の OOP の主要概念を実演します：
 * 1. 継承（Inheritance）
 * 2. ポリモーフィズム（Polymorphism）
 * 3. 抽象クラス（Abstract Class）
 * 4. カプセル化（Encapsulation）
 * 5. インターフェース（Interface）
 */
public class OOPDemo {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║  Javaオブジェクト指向プログラミング デモ          ║");
        System.out.println("╚═══════════════════════════════════════════════════╝\n");

        // ========================================
        // 1. 継承とカプセル化のデモ
        // ========================================
        demonstrateInheritanceAndEncapsulation();

        // ========================================
        // 2. ポリモーフィズムのデモ
        // ========================================
        demonstratePolymorphism();

        // ========================================
        // 3. インターフェースのデモ
        // ========================================
        demonstrateInterface();

        // ========================================
        // 4. Zoo でのポリモーフィズム総合デモ
        // ========================================
        demonstrateZoo();

        System.out.println("\n╔═══════════════════════════════════════════════════╗");
        System.out.println("║  デモ終了！                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
    }

    /**
     * 継承とカプセル化のデモ
     */
    private static void demonstrateInheritanceAndEncapsulation() {
        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.println("│ 1. 継承とカプセル化のデモ                        │");
        System.out.println("└─────────────────────────────────────────────────┘\n");

        // Dog インスタンスの作成
        Dog pochi = new Dog("ポチ", 3, 15.5, "柴犬");

        System.out.println("【カプセル化】private フィールドは getter でアクセス:");
        System.out.println("名前: " + pochi.getName());
        System.out.println("年齢: " + pochi.getAge());
        System.out.println("体重: " + pochi.getWeight() + "kg");
        System.out.println("犬種: " + pochi.getBreed());

        System.out.println("\n【継承】親クラス Animal のメソッドを呼び出し:");
        pochi.sleep();

        System.out.println("\n【継承】子クラス Dog 固有のメソッドを呼び出し:");
        pochi.fetch();
        pochi.wagTail();

        System.out.println("\n【カプセル化】setter でバリデーション付きで値を設定:");
        pochi.setAge(4);
        pochi.haveBirthday();

        System.out.println();
    }

    /**
     * ポリモーフィズムのデモ
     */
    private static void demonstratePolymorphism() {
        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.println("│ 2. ポリモーフィズムのデモ                        │");
        System.out.println("└─────────────────────────────────────────────────┘\n");

        // ポリモーフィズム：親クラスの型で子クラスのインスタンスを保持
        Animal animal1 = new Dog("タロウ", 5, 20.0, "ゴールデンレトリバー");
        Animal animal2 = new Cat("ミケ", 2, 4.5, true);

        System.out.println("【ポリモーフィズム】同じ Animal 型でも、実際の型に応じた動作:");
        System.out.println(animal1.getName() + "の鳴き声: " + animal1.makeSound());
        System.out.println(animal2.getName() + "の鳴き声: " + animal2.makeSound());

        System.out.println("\n【ポリモーフィズム】move() メソッドも実際の型に応じて動作:");
        animal1.move();
        animal2.move();

        System.out.println("\n【ポリモーフィズム】親クラスの共通メソッドも呼び出せる:");
        animal1.sleep();
        animal2.sleep();

        System.out.println("\n【ダウンキャスト】具象クラス固有のメソッドを呼ぶには型変換が必要:");
        if (animal1 instanceof Dog) {
            Dog dog = (Dog) animal1;
            dog.fetch();
        }

        if (animal2 instanceof Cat) {
            Cat cat = (Cat) animal2;
            cat.purr();
        }

        System.out.println();
    }

    /**
     * インターフェースのデモ
     */
    private static void demonstrateInterface() {
        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.println("│ 3. インターフェースのデモ                        │");
        System.out.println("└─────────────────────────────────────────────────┘\n");

        TrainedDog rex = new TrainedDog("レックス", 2, 25.0, "ジャーマンシェパード");

        System.out.println("【インターフェース】Trainable を実装した TrainedDog:");
        rex.displayInfo();

        System.out.println("\n【トレーニング】コマンドを教える:");
        rex.train("お座り");
        rex.train("待て");
        rex.train("伏せ");

        System.out.println("\n【トレーニング】教えたコマンドを実行:");
        rex.performCommand("お座り");
        rex.performCommand("待て");

        System.out.println("\n【トレーニング】知らないコマンドを実行しようとすると:");
        rex.performCommand("バック転");

        System.out.println("\n【ご褒美】トレーニング後のご褒美:");
        rex.giveReward();

        System.out.println("\n【継承とインターフェースの組み合わせ】");
        System.out.println("TrainedDog は Dog を継承し、Trainable を実装:");
        System.out.println("- Animal のメソッド: " + rex.makeSound());
        rex.fetch(); // Dog のメソッド
        rex.showKnownCommands(); // TrainedDog のメソッド

        System.out.println();
    }

    /**
     * Zoo でのポリモーフィズム総合デモ
     */
    private static void demonstrateZoo() {
        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.println("│ 4. Zoo でのポリモーフィズム総合デモ              │");
        System.out.println("└─────────────────────────────────────────────────┘\n");

        // 動物園を作成
        Zoo zoo = new Zoo("サニーサイド動物園");

        // 様々な動物を追加
        Dog pochi = new Dog("ポチ", 3, 15.5, "柴犬");
        Dog taro = new Dog("タロウ", 5, 22.0, "ハスキー");
        Cat mike = new Cat("ミケ", 2, 4.5, true);
        Cat tora = new Cat("トラ", 4, 5.2, false);
        TrainedDog rex = new TrainedDog("レックス", 2, 25.0, "ジャーマンシェパード");

        // レックスにコマンドを教える
        rex.train("お座り");
        rex.train("待て");
        rex.train("お手");

        // 動物園に追加
        zoo.addAnimal(pochi);
        zoo.addAnimal(taro);
        zoo.addAnimal(mike);
        zoo.addAnimal(tora);
        zoo.addAnimal(rex);

        // 統計情報
        zoo.displayStatistics();

        // 動物一覧
        zoo.displayAllAnimals();

        // ポリモーフィズムの実践：すべての動物に対して同じメソッドを呼ぶ
        zoo.makeAllAnimalsSpeak();
        zoo.makeAllAnimalsMove();

        // 型に応じた特殊な操作
        zoo.playWithDogs();
        zoo.pamperCats();

        // インターフェースを実装している動物だけを対象にした操作
        zoo.trainingSession();

        // 食事の時間
        zoo.feedAllAnimals(1.0);

        // 詳細情報の表示
        zoo.displayDetailedInfo();

        System.out.println("【総括】");
        System.out.println("Zoo クラスは List<Animal> で異なる型の動物を管理し、");
        System.out.println("ポリモーフィズムにより各動物の実際の型に応じた");
        System.out.println("適切なメソッドが呼び出されることを実演しました。");
    }
}
