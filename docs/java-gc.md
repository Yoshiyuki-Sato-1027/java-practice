# Java ガベージコレクション (GC)

## 概要

ガベージコレクション（Garbage Collection, GC）は、Java仮想マシン（JVM）が自動的にメモリを管理する仕組みです。プログラマが明示的にメモリを解放する必要がなく、使用されなくなったオブジェクトを自動的に検出して回収します。

## GCの基本概念

### ヒープメモリの構造

Javaのヒープメモリは主に以下の領域に分かれています：

```
+------------------+
|   Young世代       |  新しいオブジェクトが割り当てられる領域
|  +------------+  |
|  | Eden       |  |  オブジェクトが最初に作成される場所
|  +------------+  |
|  | Survivor 0 |  |  Minor GCを生き延びたオブジェクト
|  | Survivor 1 |  |
|  +------------+  |
+------------------+
|   Old世代         |  長期間生存しているオブジェクトが移動する領域
+------------------+
|   Metaspace      |  クラスメタデータが格納される領域（Java 8以降）
+------------------+
```

### GCの種類

1. **Minor GC (Young GC)**
   - Young世代で発生するGC
   - 頻繁に実行されるが、停止時間は短い
   - Eden領域が満杯になると発生

2. **Major GC (Full GC)**
   - Old世代を含むヒープ全体のGC
   - 実行頻度は低いが、停止時間が長い
   - Old世代が満杯になると発生

## 主要なGCアルゴリズム

### 1. Serial GC

```
java -XX:+UseSerialGC
```

- **特徴**: シングルスレッドで動作
- **用途**: 小規模アプリケーション、シングルコアCPU
- **メリット**: シンプルで軽量
- **デメリット**: 停止時間が長い

### 2. Parallel GC (Throughput GC)

```
java -XX:+UseParallelGC
```

- **特徴**: マルチスレッドでYoung世代をGC
- **用途**: バッチ処理、スループット重視のアプリケーション
- **メリット**: 高いスループット
- **デメリット**: 停止時間の予測が難しい

### 3. CMS (Concurrent Mark Sweep) GC

```
java -XX:+UseConcMarkSweepGC
```

- **特徴**: アプリケーション実行と並行してGC
- **用途**: レスポンスタイム重視のアプリケーション
- **メリット**: 停止時間が短い
- **デメリット**: CPUリソースを消費、フラグメンテーション
- **注意**: Java 9で非推奨、Java 14で削除

### 4. G1GC (Garbage First GC)

```
java -XX:+UseG1GC
```

- **特徴**: ヒープを複数のリージョンに分割して管理
- **用途**: Java 9以降のデフォルトGC、大規模ヒープ
- **メリット**:
  - 停止時間の予測可能性が高い
  - フラグメンテーションが少ない
  - 大規模ヒープに対応
- **主要オプション**:
  ```
  -XX:MaxGCPauseMillis=200  # 目標停止時間（デフォルト200ms）
  -XX:G1HeapRegionSize=n    # リージョンサイズ
  ```

### 5. ZGC (Z Garbage Collector)

```
java -XX:+UseZGC
```

- **特徴**: 超低レイテンシGC（Java 11で実験的、Java 15で本番対応）
- **用途**: 非常に大規模なヒープ（数TB）、低レイテンシ要求
- **メリット**:
  - 停止時間が10ms以下
  - ヒープサイズに依存しない停止時間
  - Colored Pointersとロードバリアを使用
- **デメリット**: メモリ使用量が多い

### 6. Shenandoah GC

```
java -XX:+UseShenandoahGC
```

- **特徴**: ZGCと同様の低レイテンシGC
- **用途**: 低レイテンシ要求のアプリケーション
- **メリット**: 停止時間が短く一定
- **デメリット**: スループットがやや低い

## GCチューニングの基本

### ヒープサイズの設定

```bash
# 初期ヒープサイズと最大ヒープサイズ
java -Xms2g -Xmx4g

# Young世代とOld世代の比率
java -XX:NewRatio=2  # Old:Young = 2:1

# Young世代のサイズを直接指定
java -Xmn512m
```

### GCログの出力

Java 8以前：
```bash
java -XX:+PrintGCDetails \
     -XX:+PrintGCDateStamps \
     -Xloggc:gc.log
```

Java 9以降：
```bash
java -Xlog:gc*:file=gc.log:time,level,tags
```

### 主要なチューニングパラメータ

```bash
# G1GCの例
java -Xms4g -Xmx4g \
     -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -XX:G1HeapRegionSize=16m \
     -XX:InitiatingHeapOccupancyPercent=45 \
     -XX:+ParallelRefProcEnabled \
     -Xlog:gc*:file=gc.log:time
```

## GCログの分析

### 主要な指標

1. **スループット**: アプリケーション実行時間 / 総実行時間
2. **レイテンシ**: GC停止時間の長さと頻度
3. **メモリフットプリント**: 使用メモリ量

### GCログの例（G1GC）

```
[2025-01-15T10:15:30.123+0900] GC(123) Pause Young (Normal) (G1 Evacuation Pause)
[2025-01-15T10:15:30.123+0900] GC(123) Using 4 workers of 8 for evacuation
[2025-01-15T10:15:30.145+0900] GC(123) Pause Young (Normal) (G1 Evacuation Pause) 512M->128M(1024M) 22.456ms
```

読み方：
- `Pause Young`: Minor GCが発生
- `512M->128M(1024M)`: GC前のヒープ使用量->GC後のヒープ使用量(総ヒープサイズ)
- `22.456ms`: GC停止時間

## GC選択のガイドライン

| 要件 | 推奨GC | 理由 |
|------|--------|------|
| デフォルト・汎用 | G1GC | バランスが良く、ほとんどのケースで適用可能 |
| 最大スループット | Parallel GC | バッチ処理などで有効 |
| 低レイテンシ（通常） | G1GC | 停止時間の予測可能性が高い |
| 超低レイテンシ | ZGC / Shenandoah | 10ms以下の停止時間が必要な場合 |
| 小規模・組み込み | Serial GC | シンプルで軽量 |
| 大規模ヒープ（>32GB） | ZGC / Shenandoah | ヒープサイズに依存しない停止時間 |

## ベストプラクティス

1. **開発時**: デフォルト設定で開始
2. **測定**: 本番環境に近い条件でGCログを収集
3. **分析**: GCViewerなどのツールでログを分析
4. **チューニング**: ボトルネックに応じて段階的に調整
5. **検証**: 変更の効果を測定

### 避けるべきパターン

- むやみにヒープサイズを大きくする
- 理解せずにGCパラメータを大量に設定する
- GCログを取らずにチューニングする
- 本番環境で未検証のGCアルゴリズムを使用する

## モニタリングツール

1. **JConsole**: JDK付属のGUIモニタリングツール
2. **VisualVM**: 高機能なプロファイラー
3. **Java Mission Control**: 本番環境向けプロファイラー
4. **GCViewer**: GCログ分析ツール
5. **Prometheus + Grafana**: メトリクス収集と可視化

## まとめ

- GCは自動メモリ管理の仕組みで、Javaの重要な機能
- Java 9以降はG1GCがデフォルト
- 低レイテンシが必要な場合はZGCやShenandoahを検討
- チューニングは測定と分析に基づいて段階的に実施
- GCログは必ず取得して分析する

## 参考リソース

- [Oracle Java GC Tuning Guide](https://docs.oracle.com/en/java/javase/)
- [ZGC Documentation](https://wiki.openjdk.java.net/display/zgc)
- [G1GC Documentation](https://www.oracle.com/technical-resources/articles/java/g1gc.html)
