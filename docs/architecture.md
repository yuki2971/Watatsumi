# 構造の地図へようこそ（2026-03-02）

## 🎯 目的

1. 構造理解を優先し、各クラスの責務を明文化する
2. 依存関係を記録し、変更時の影響範囲を可視化する
3. 抽象レベルを揃え、概念の混線を防ぐ

---

## 📌 このドキュメントの役割

- 思想ログ（Decision Log）と実装構造を接続する
- API理解ではなく「自分の設計理解」を優先する
- クラスの存在理由を言語化する

# Minecraft Mod における「層」の整理

## ① 物理層（実行環境の層）

これは「どこで動くか」という分類。

### ■ Common（共通層）
- クライアントでもサーバーでも動く
- ゲームのルールを書く場所
- アイテム登録
- ブロック登録
- レシピ
- ゲームロジック
- イベント処理（ロジック系）

例：
- Watatsumi.java
- ModItems
- ModLootModifiers
- ModEvents

---

### ■ Client（クライアント層）
- プレイヤーの画面側で動く
- 見た目や操作を扱う

含まれるもの：
- GUI
- レンダラー
- キーバインド
- パーティクル
- モデル関連

例：
- WatatsumiClient.java
- Client専用イベント

---

### ■ Server（サーバー層）
- Dedicated Serverで動く
- 画面はない

含まれるもの：
- サーバー専用処理
- 権限管理
- ネットワーク処理

Watatsumiでは現在ほぼ未使用

---

## ② 設計層（論理的な役割分離）

これは「何の責務か」という分類。
物理層とは別軸。

### ■ Domain層（ゲーム概念）
- ゲームの本質的な概念
- ルールの中心
- 例：MarineResource

---

### ■ Registry層
- 登録処理だけを担当
- ゲーム概念そのものは持たない

例：
- ModItems
- ModBlocks

---

### ■ Infrastructure層
- NeoForgeやAPIとの接続部分
- イベント登録など

例：
- Watatsumi.java
- Event登録クラス

---

### ■ Presentation層
- 表示やUI
- 見た目

例：
- WatatsumiClient.java
- GUIクラス

---

## 重要ポイント

物理層 = どこで動くか
設計層 = 何をするか

この2つは別軸で考える。

---

## Watatsumiの例

MarineResource は：

- 物理層 → Common
- 設計層 → Domain


### テンプレ

■ 責務：
■ 依存：
■ 呼び出し元：
■ 呼び出しタイミング：
■ 設計上の意図：
■ 属する物理層：
■ 属する設計層：

main

### Watatsumi.java

■ 責務：
- MOD全体のエントリーポイント (Common層のエントリーポイント)
- レジストリ登録の起点
- NeoForgeイベント登録
- Datagenリスナー登録

■ 依存：
- NeoForge Mod EventBus
- NeoForge Global EventBus
- ModItems
- ModLootModifiers
- ModEvents
- Datagen関連クラス（Recipe / ItemModel / LootModifier Provider）

■ 呼び出し元：
- NeoForge本体

■ 呼び出しタイミング：
- MODロード時（コンストラクタ実行）
- runData実行時（gatherData実行）

■ 設計上の意図：
- Common層のエントリーポイントとして機能させる
- 初期化処理を直接書かず、登録に徹する
- 実装詳細を registry / event / datagen に分離する
- 依存を上位に集約し、下位から逆参照させない設計にする
■ 属する物理層：
■ 属する設計層：


### WatatsumiClient.Java

■ 責務：
- クライアント専用初期化処理の定義
- クライアント側拡張ポイント（Config GUI など）の登録
- Client専用イベントの受け口
■ 依存：
- Watatsumi.MOD_ID
- NeoForge Client Lifecycle（FMLClientSetupEvent）
- NeoForge GUI拡張（IConfigScreenFactory, ConfigurationScreen）
- Dist.CLIENT（物理的クライアント環境）
- サーバー側コードには依存しない（重要）。
■ 呼び出し元：
- NeoForge（Modロード時）
- NeoForge EventBus（ClientSetupイベント発火時）
■ 呼び出しタイミング：
- Modロード時（Client側のみ）
  → コンストラクタ実行
  → Config画面登録
- ClientSetupフェーズ
  → onClientSetup 実行
■ 設計上の意図：
- 「クライアント専用処理」をサーバーから完全分離する
- Dedicated Serverで絶対ロードされない安全設計
- 将来：
  → レンダラー登録
  → メニュー画面登録
  → キーバインド登録
  → カラーハンドラ登録

### Config.Java

■ 責務：
■ 依存：
■ 呼び出し元：
■ 呼び出しタイミング：
■ 設計上の意図：


datagen
### ItemModelsProvider.Java

■ 責務：
■ 依存：
■ 呼び出し元：
■ 呼び出しタイミング：
■ 設計上の意図：

### WatatsumiRecipeProvider.Java

■ 責務：
■ 依存：
■ 呼び出し元：
■ 呼び出しタイミング：
■ 設計上の意図：

### WatatsumiGlobalLootModifier.Java

■ 責務：
■ 依存：
■ 呼び出し元：
■ 呼び出しタイミング：
■ 設計上の意図：

event
### ModEvents.Java

■ 責務：
■ 依存：
■ 呼び出し元：
■ 呼び出しタイミング：
■ 設計上の意図：

### MarineLootModifier.Java

■ 責務：
■ 依存：
■ 呼び出し元：
■ 呼び出しタイミング：
■ 設計上の意図：

item
### SeawaterBucket.Java

■ 責務：
■ 依存：
■ 呼び出し元：
■ 呼び出しタイミング：
■ 設計上の意図：

registry
### ModBlocks.java

■ 責務：
- MOD内ブロックの登録を一元管理するクラス
- 将来追加される機械・構造物の登録起点

■ 依存：
- NeoForge DeferredRegister（将来）
- Watatsumi.MOD_ID

■ 呼び出し元：
- Watatsumi.java（コンストラクタ内 register）

■ 呼び出しタイミング：
- MODロード時（レジストリ登録フェーズ）

■ 設計上の意図：
- ブロック登録を他クラスから分離する
- Watatsumiの拡張（機械系追加）に備えた構造確保
- 登録責務をregistry層に限定する

■ 属する物理層：
- Common

■ 属する設計層：
- Registry
### ModItems.java

■ 責務：
- MOD内アイテムの登録を一元管理する
- DeferredRegisterを用いて安全にItemを登録する
- Watatsumi内アイテム定義の集中管理

■ 依存：
- Watatsumi.MOD_ID
- NeoForge DeferredRegister
- NeoForge IEventBus
- Registries.ITEM
- SeawaterBucketItem（カスタムアイテム）

■ 呼び出し元：
- Watatsumi.java（コンストラクタ内で register 呼び出し）

■ 呼び出しタイミング：
- MODロード時（レジストリ登録フェーズ）

■ 設計上の意図：
- 登録責務をregistry層に限定する
- アイテム生成ロジックを直接持たず、定義のみを行う
- Common層で安全にアイテムを共有可能にする
- 将来的なアイテム追加を一箇所に集約する

■ 属する物理層：
- Common

■ 属する設計層：
- Registry
### ModLootModifiers.java

■ 責務：
- GlobalLootModifierのシリアライザ（MapCodec）を登録する
- データパックからLootModifierを読み込める状態にする

■ 依存：
- Watatsumi.MOD_ID
- NeoForge DeferredRegister
- NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS
- MarineLootModifier（CODEC）

■ 呼び出し元：
- Watatsumi.java（コンストラクタ内で LOOT_MODIFIERS.register 呼び出し）

■ 呼び出しタイミング：
- MODロード時（レジストリ登録フェーズ）

■ 設計上の意図：
- LootModifier本体と登録処理を分離する
- データ駆動型設計（datapack）を可能にする
- registry層に責務を限定する

■ 属する物理層：
- Common

■ 属する設計層：
- Registry
### ModTags.Java

### ModTags.java

■ 責務：
- MOD内で使用するTagKeyの定義を一元管理する
- datapackで定義されたタグをJava側から参照可能にする

■ 依存：
- Registries.ENTITY_TYPE
- TagKey
- ResourceLocation

■ 呼び出し元：
- MarineLootModifier 等のロジッククラス

■ 呼び出しタイミング：
- クラスロード時（定数定義のみ）
- 実際の評価はタグ参照時

■ 設計上の意図：
- タグ名のハードコード分散を防ぐ
- タグ参照を型安全に扱う
- datapack駆動設計を支援する

■ 属する物理層：
- Common

■ 属する設計層：
- Registry