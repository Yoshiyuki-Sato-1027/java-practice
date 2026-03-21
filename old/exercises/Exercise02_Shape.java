package old.exercises;

/**
 * 【問題2】インターフェースと抽象クラスの練習：図形クラス
 *
 * <h2>問題の目的</h2>
 * <ul>
 * <li>インターフェースの定義と実装</li>
 * <li>抽象クラスとインターフェースの組み合わせ</li>
 * <li>複数のインターフェース実装</li>
 * <li>定数の定義（interface の static final）</li>
 * </ul>
 *
 * <h2>課題</h2>
 * <p>
 * 図形を扱うクラス群を実装してください。
 * </p>
 *
 * <h3>要件1: Shape インターフェース</h3>
 * <ul>
 * <li>メソッド: double calculateArea() - 面積を計算</li>
 * <li>メソッド: double calculatePerimeter() - 周囲の長さを計算</li>
 * <li>メソッド: String getShapeName() - 図形名を返す</li>
 * </ul>
 *
 * <h3>要件2: Drawable インターフェース</h3>
 * <ul>
 * <li>メソッド: void draw() - 図形を描画（文字で表現）</li>
 * </ul>
 *
 * <h3>要件3: Circle クラス（Shape と Drawable を実装）</h3>
 * <ul>
 * <li>フィールド: radius (double)</li>
 * <li>定数: PI = 3.14159</li>
 * <li>面積 = π × r²</li>
 * <li>周囲 = 2 × π × r</li>
 * <li>draw() で "○ Circle" と表示</li>
 * </ul>
 *
 * <h3>要件4: Rectangle クラス（Shape と Drawable を実装）</h3>
 * <ul>
 * <li>フィールド: width (double), height (double)</li>
 * <li>面積 = width × height</li>
 * <li>周囲 = 2 × (width + height)</li>
 * <li>draw() で "□ Rectangle" と表示</li>
 * </ul>
 *
 * <h3>要件5: Triangle クラス（Shape と Drawable を実装）</h3>
 * <ul>
 * <li>フィールド: base (double), height (double), side1, side2, side3 (double)</li>
 * <li>面積 = (base × height) / 2</li>
 * <li>周囲 = side1 + side2 + side3</li>
 * <li>draw() で "△ Triangle" と表示</li>
 * </ul>
 *
 * <h3>要件6: ShapeCalculator クラス</h3>
 * <ul>
 * <li>静的メソッド: double getTotalArea(Shape[] shapes) - すべての図形の総面積</li>
 * <li>静的メソッド: void drawAll(Drawable[] drawables) - すべての図形を描画</li>
 * <li>静的メソッド: Shape findLargestShape(Shape[] shapes) - 最大面積の図形を返す</li>
 * </ul>
 *
 * <h2>ヒント</h2>
 * <ul>
 * <li>interface キーワードでインターフェースを定義</li>
 * <li>implements キーワードで実装</li>
 * <li>複数のインターフェースは implements Shape, Drawable のように書く</li>
 * <li>Math.PI を使うと正確な円周率が使える</li>
 * </ul>
 *
 * <h2>動作確認</h2>
 *
 * <pre>
 * Shape[] shapes = {new Circle(5.0), new Rectangle(4.0, 6.0), new Triangle(3.0, 4.0, 3.0, 4.0, 5.0)};
 *
 * System.out.println("総面積: " + ShapeCalculator.getTotalArea(shapes));
 * ShapeCalculator.drawAll((Drawable[]) shapes);
 * Shape largest = ShapeCalculator.findLargestShape(shapes);
 * System.out.println("最大の図形: " + largest.getShapeName());
 * </pre>
 */
public class Exercise02_Shape {
    public static void main(String[] args) {
        System.out.println("【問題2】図形クラスの実装");
        System.out.println("この問題では、インターフェースと抽象クラスを練習します。");
        System.out.println("\n上記のコメントを読んで、以下を実装してください：");
        System.out.println("1. Shape インターフェース");
        System.out.println("2. Drawable インターフェース");
        System.out.println("3. Circle, Rectangle, Triangle クラス");
        System.out.println("4. ShapeCalculator クラス");
        System.out.println("\n実装後、このmainメソッドでテストしてください。");

        // TODO: ここに実装したクラスのテストコードを書く
    }
}

// TODO: ここから下にインターフェースとクラスを実装してください

/*
 * Shape インターフェースを実装
 */
// interface Shape {
// // TODO: メソッドを定義
// }

/*
 * Drawable インターフェースを実装
 */
// interface Drawable {
// // TODO: メソッドを定義
// }

/*
 * Circle クラスを実装
 */
// class Circle implements Shape, Drawable {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }

/*
 * Rectangle クラスを実装
 */
// class Rectangle implements Shape, Drawable {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }

/*
 * Triangle クラスを実装
 */
// class Triangle implements Shape, Drawable {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }

/*
 * ShapeCalculator クラスを実装
 */
// class ShapeCalculator {
// // TODO: 静的メソッドを実装
// }
