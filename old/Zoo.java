package old;

import java.util.ArrayList;
import java.util.List;

/**
 * ポリモーフィズムの活用例：Zoo クラス
 *
 * 【学習ポイント】 1. List<Animal> で異なる種類の動物を同じコレクションで管理 2.
 * ポリモーフィズムにより、実際の型に応じた適切なメソッドが呼ばれる 3. instanceof 演算子で型チェック 4.
 * キャストによる具象クラス固有のメソッド呼び出し
 */
public class Zoo {
    private String name;
    private List<Animal> animals;

    /**
     * コンストラクタ
     */
    public Zoo(String name) {
        this.name = name;
        this.animals = new ArrayList<>();
    }

    /**
     * 動物を追加 ポリモーフィズム：Dog, Cat, その他 Animal サブクラスをすべて受け入れる
     */
    public void addAnimal(Animal animal) {
        animals.add(animal);
        System.out.println(animal.getName() + "が" + name + "に入園しました！");
    }

    /**
     * 動物を削除
     */
    public boolean removeAnimal(Animal animal) {
        if (animals.remove(animal)) {
            System.out.println(animal.getName() + "が" + name + "から退園しました。");
            return true;
        }
        return false;
    }

    /**
     * すべての動物の鳴き声を聞く ポリモーフィズムの実践例
     */
    public void makeAllAnimalsSpeak() {
        System.out.println("\n=== " + name + "のコンサート ===");
        for (Animal animal : animals) {
            // ポリモーフィズム：実際の型（Dog, Cat等）に応じた makeSound() が呼ばれる
            System.out.println(animal.getName() + ": " + animal.makeSound());
        }
        System.out.println("========================\n");
    }

    /**
     * すべての動物を移動させる ポリモーフィズムの実践例
     */
    public void makeAllAnimalsMove() {
        System.out.println("\n=== 運動の時間 ===");
        for (Animal animal : animals) {
            // ポリモーフィズム：実際の型に応じた move() が呼ばれる
            animal.move();
        }
        System.out.println("==================\n");
    }

    /**
     * すべての動物を寝かせる
     */
    public void makeAllAnimalsSleep() {
        System.out.println("\n=== おやすみの時間 ===");
        for (Animal animal : animals) {
            animal.sleep();
        }
        System.out.println("======================\n");
    }

    /**
     * 犬だけに特殊な行動をさせる instanceof 演算子とキャストの例
     */
    public void playWithDogs() {
        System.out.println("\n=== 犬との遊び時間 ===");
        for (Animal animal : animals) {
            // 型チェック
            if (animal instanceof Dog) {
                Dog dog = (Dog) animal; // ダウンキャスト
                dog.wagTail();
                dog.fetch();
            }
        }
        System.out.println("======================\n");
    }

    /**
     * 猫だけに特殊な行動をさせる
     */
    public void pamperCats() {
        System.out.println("\n=== 猫との触れ合い時間 ===");
        for (Animal animal : animals) {
            if (animal instanceof Cat) {
                Cat cat = (Cat) animal;
                cat.purr();
                cat.scratch();
            }
        }
        System.out.println("==========================\n");
    }

    /**
     * 訓練された犬のトレーニングセッション インターフェース Trainable の活用例
     */
    public void trainingSession() {
        System.out.println("\n=== トレーニングセッション ===");
        for (Animal animal : animals) {
            // Trainable インターフェースを実装しているかチェック
            if (animal instanceof Trainable) {
                Trainable trainableAnimal = (Trainable) animal;
                System.out.println(animal.getName() + "のトレーニング（レベル: " + trainableAnimal.getTrainingLevel() + "）");
            }
        }
        System.out.println("==============================\n");
    }

    /**
     * すべての動物に食事を与える
     */
    public void feedAllAnimals(double foodWeight) {
        System.out.println("\n=== 食事の時間 ===");
        for (Animal animal : animals) {
            animal.eat(foodWeight);
        }
        System.out.println("==================\n");
    }

    /**
     * 動物園の統計情報を表示
     */
    public void displayStatistics() {
        System.out.println("\n=== " + name + " 統計情報 ===");
        System.out.println("総動物数: " + animals.size());

        int dogCount = 0;
        int catCount = 0;
        int trainedDogCount = 0;
        double totalWeight = 0;
        int totalAge = 0;

        for (Animal animal : animals) {
            if (animal instanceof TrainedDog) {
                trainedDogCount++;
                dogCount++;
            } else if (animal instanceof Dog) {
                dogCount++;
            } else if (animal instanceof Cat) {
                catCount++;
            }
            totalWeight += animal.getWeight();
            totalAge += animal.getAge();
        }

        System.out.println("犬: " + dogCount + "匹（うち訓練済み: " + trainedDogCount + "匹）");
        System.out.println("猫: " + catCount + "匹");
        System.out.printf("平均体重: %.2fkg\n", animals.isEmpty() ? 0 : totalWeight / animals.size());
        System.out.printf("平均年齢: %.1f歳\n", animals.isEmpty() ? 0 : (double) totalAge / animals.size());
        System.out.println("===========================\n");
    }

    /**
     * すべての動物の情報を表示
     */
    public void displayAllAnimals() {
        System.out.println("\n=== " + name + " 動物一覧 ===");
        if (animals.isEmpty()) {
            System.out.println("動物はいません。");
        } else {
            for (int i = 0; i < animals.size(); i++) {
                System.out.println((i + 1) + ". " + animals.get(i));
            }
        }
        System.out.println("============================\n");
    }

    /**
     * 動物の詳細情報を表示
     */
    public void displayDetailedInfo() {
        System.out.println("\n=== " + name + " 詳細情報 ===");
        for (Animal animal : animals) {
            animal.displayInfo();
        }
        System.out.println("===============================\n");
    }

    // getter
    public String getName() {
        return name;
    }

    public int getAnimalCount() {
        return animals.size();
    }
}
