package old;

/**
 * インターフェースの例：Trainable
 *
 * 【学習ポイント】 1. interface キーワードで定義 2. メソッドのシグネチャのみを定義（実装はない） 3.
 * 複数のインターフェースを実装可能（多重継承の代替） 4. 「訓練可能である」という能力を表現
 */
public interface Trainable {
    /**
     * トレーニングを行う
     *
     * @param command
     *            教えるコマンド
     */
    void train(String command);

    /**
     * コマンドを実行する
     *
     * @param command
     *            実行するコマンド
     * @return 成功したかどうか
     */
    boolean performCommand(String command);

    /**
     * トレーニングレベルを取得
     *
     * @return トレーニングレベル（0-10）
     */
    int getTrainingLevel();

    /**
     * ご褒美を与える
     */
    void giveReward();
}
