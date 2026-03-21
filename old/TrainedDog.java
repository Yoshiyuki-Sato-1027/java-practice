package old;

import java.util.HashSet;
import java.util.Set;

/**
 * インターフェース実装の例：TrainedDog
 *
 * 【学習ポイント】
 * 1. extends で Dog を継承し、implements で Trainable を実装
 * 2. クラス継承とインターフェース実装の組み合わせ
 * 3. インターフェースのすべてのメソッドを実装する必要がある
 */
public class TrainedDog extends Dog implements Trainable {
    // Trainable の実装に必要なフィールド
    private int trainingLevel;
    private Set<String> knownCommands; // 知っているコマンド

    /**
     * コンストラクタ
     */
    public TrainedDog(String name, int age, double weight, String breed) {
        super(name, age, weight, breed);
        this.trainingLevel = 0;
        this.knownCommands = new HashSet<>();
    }

    /**
     * Trainable インターフェースの実装：トレーニング
     */
    @Override
    public void train(String command) {
        if (command == null || command.trim().isEmpty()) {
            System.out.println("有効なコマンドを指定してください。");
            return;
        }

        if (knownCommands.contains(command)) {
            System.out.println(getName() + "はすでに「" + command + "」を知っています。");
        } else {
            knownCommands.add(command);
            trainingLevel++;
            System.out.println(getName() + "は「" + command + "」を覚えました！");
            System.out.println("トレーニングレベル: " + trainingLevel);
        }
    }

    /**
     * Trainable インターフェースの実装：コマンド実行
     */
    @Override
    public boolean performCommand(String command) {
        if (knownCommands.contains(command)) {
            System.out.println(getName() + "は「" + command + "」を実行しました！");
            executeCommand(command);
            return true;
        } else {
            System.out.println(getName() + "は「" + command + "」を知りません。まずトレーニングしてください。");
            return false;
        }
    }

    /**
     * コマンド実行の具体的な内容
     */
    private void executeCommand(String command) {
        switch (command.toLowerCase()) {
            case "お座り":
            case "sit":
                System.out.println("  → " + getName() + "はお座りしました。");
                break;
            case "待て":
            case "stay":
                System.out.println("  → " + getName() + "はその場で待っています。");
                break;
            case "伏せ":
            case "down":
                System.out.println("  → " + getName() + "は伏せをしました。");
                break;
            case "お手":
            case "shake":
                System.out.println("  → " + getName() + "はお手をしました。");
                break;
            case "おかわり":
                System.out.println("  → " + getName() + "はおかわりをしました。");
                break;
            default:
                System.out.println("  → " + getName() + "は「" + command + "」を実行しています。");
        }
    }

    /**
     * Trainable インターフェースの実装：トレーニングレベル取得
     */
    @Override
    public int getTrainingLevel() {
        return trainingLevel;
    }

    /**
     * Trainable インターフェースの実装：ご褒美
     */
    @Override
    public void giveReward() {
        System.out.println(getName() + "におやつをあげました！");
        wagTail(); // Dog クラスから継承したメソッド
    }

    /**
     * 知っているコマンドを表示
     */
    public void showKnownCommands() {
        if (knownCommands.isEmpty()) {
            System.out.println(getName() + "はまだコマンドを覚えていません。");
        } else {
            System.out.println(getName() + "が知っているコマンド:");
            for (String cmd : knownCommands) {
                System.out.println("  - " + cmd);
            }
        }
    }

    /**
     * displayInfo のオーバーライド
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("トレーニングレベル: " + trainingLevel);
        System.out.println("習得コマンド数: " + knownCommands.size());
    }
}
