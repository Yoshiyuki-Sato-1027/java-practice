package old.exercises;

/**
 * 【問題1】継承とポリモーフィズムの練習：乗り物クラス階層
 *
 * <h2>問題の目的</h2>
 * <ul>
 * <li>抽象クラスの定義と使用</li>
 * <li>継承の実装</li>
 * <li>ポリモーフィズムの理解</li>
 * <li>メソッドのオーバーライド</li>
 * </ul>
 *
 * <h2>課題</h2>
 * <p>
 * 以下の要件に従って、乗り物（Vehicle）のクラス階層を実装してください。
 * </p>
 *
 * <h3>要件1: Vehicle 抽象クラス</h3>
 * <ul>
 * <li>フィールド: brand (String), speed (double)</li>
 * <li>コンストラクタ: brand を受け取る</li>
 * <li>抽象メソッド: String getVehicleType()</li>
 * <li>抽象メソッド: void accelerate(double amount)</li>
 * <li>具象メソッド: void displayInfo() - ブランドと現在速度を表示</li>
 * <li>getter/setter を適切に実装</li>
 * </ul>
 *
 * <h3>要件2: Car クラス（Vehicle を継承）</h3>
 * <ul>
 * <li>追加フィールド: numberOfDoors (int)</li>
 * <li>getVehicleType() を実装（"Car" を返す）</li>
 * <li>accelerate() を実装（speed に amount を加算、最高速度180km/h）</li>
 * </ul>
 *
 * <h3>要件3: Motorcycle クラス（Vehicle を継承）</h3>
 * <ul>
 * <li>追加フィールド: hasSidecar (boolean)</li>
 * <li>getVehicleType() を実装（"Motorcycle" を返す）</li>
 * <li>accelerate() を実装（speed に amount * 1.2 を加算、最高速度200km/h）</li>
 * </ul>
 *
 * <h3>要件4: VehicleManager クラス</h3>
 * <ul>
 * <li>List&lt;Vehicle&gt; で複数の乗り物を管理</li>
 * <li>addVehicle(Vehicle v) メソッド</li>
 * <li>accelerateAll(double amount) メソッド - すべての乗り物を加速</li>
 * <li>displayAllVehicles() メソッド - すべての乗り物の情報を表示</li>
 * </ul>
 *
 * <h2>ヒント</h2>
 * <ul>
 * <li>abstract キーワードを使って抽象クラスを定義</li>
 * <li>extends キーワードで継承</li>
 * <li>@Override アノテーションを使用</li>
 * <li>最高速度チェックは accelerate() 内で実装</li>
 * </ul>
 *
 * <h2>動作確認</h2>
 *
 * <pre>
 * VehicleManager manager = new VehicleManager();
 * manager.addVehicle(new Car("Toyota", 4));
 * manager.addVehicle(new Motorcycle("Harley", false));
 * manager.accelerateAll(50);
 * manager.displayAllVehicles();
 * </pre>
 */

public class Exercise01_Vehicle {
    public static void main(String[] args) {
        System.out.println("【問題1】乗り物クラス階層の実装");
        System.out.println("この問題では、継承とポリモーフィズムを練習します。");
        System.out.println("\n上記のコメントを読んで、以下のクラスを実装してください：");
        System.out.println("1. Vehicle 抽象クラス");
        System.out.println("2. Car クラス");
        System.out.println("3. Motorcycle クラス");
        System.out.println("4. VehicleManager クラス");
        System.out.println("\n実装後、このmainメソッドでテストしてください。");

        // TODO: ここに実装したクラスのテストコードを書く
        // VehicleManager manager = new VehicleManager();
        // manager.addVehicle(new Car("Toyota", 4));
        // manager.addVehicle(new Motorcycle("Harley", false));
        // manager.accelerateAll(50);
        // manager.displayAllVehicles();
    }
}

// TODO: ここから下に Vehicle, Car, Motorcycle, VehicleManager クラスを実装してください
// * <h3>要件1: Vehicle 抽象クラス</h3>
//        * <ul>
// * <li>フィールド: brand (String), speed (double)</li>
//        * <li>コンストラクタ: brand を受け取る</li>
//        * <li>抽象メソッド: String getVehicleType()</li>
//        * <li>抽象メソッド: void accelerate(double amount)</li>
//        * <li>具象メソッド: void displayInfo() - ブランドと現在速度を表示</li>
//        * <li>getter/setter を適切に実装</li>
//        * </ul>
/*
 * Vehicle 抽象クラスを実装
 */
abstract class Vehicle {
    // TODO: フィールド、コンストラクタ、メソッドを実装
    private String brand;
    private double speed;

    // コンストラクタ
    public Vehicle(String brand, double speed) {
        this.brand = brand;
        this.speed = speed;
    }


    public abstract String getVehicleType();

    public abstract void accelerate(double amount);

    public void displayInfo() {
        System.out.println("Brand: " + brand + ", Speed: " + speed);
    }
}


/*
 * Car クラスを実装
 */
// * <h3>要件2: Car クラス（Vehicle を継承）</h3>
//        * <ul>
// * <li>追加フィールド: numberOfDoors (int)</li>
//        * <li>getVehicleType() を実装（"Car" を返す）</li>
//        * <li>accelerate() を実装（speed に amount を加算、最高速度180km/h）</li>
//        * </ul>
class Car extends Vehicle {
    // TODO: フィールド、コンストラクタ、メソッドを実装
    private int numberOfDoors;

    public Car(String brand, double speed, int numberOfDoors) {
        super(brand, speed);  // 親クラスのコンストラクタを呼ぶ
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }

    @Override
    public void accelerate(double amount) {
        // speedにアクセスするためgetterが必要か、protectedにする
        double newSpeed = getSpeed() + amount;

        // 最高速度180km/hの制限
        if (newSpeed > 180) {
            setSpeed(180);
        } else {
            setSpeed(newSpeed);
        }
    }

    // Getter
    public int getNumberOfDoors() {
        return numberOfDoors;
    }
}

/*
 * Motorcycle クラスを実装
 */
// class Motorcycle extends Vehicle {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }

/*
 * VehicleManager クラスを実装
 */
// class VehicleManager {
// // TODO: フィールド、コンストラクタ、メソッドを実装
// }
