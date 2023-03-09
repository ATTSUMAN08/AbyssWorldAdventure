package me.attsuman08.rpg.database.table

import me.attsuman08.rpg.database.textArray
import org.jetbrains.exposed.sql.Table

object ItemData : Table() {
    // アイテムの名前
    val name = text("name")
    override val primaryKey = PrimaryKey(name)

    // アイテムの説明
    val lore = textArray("lore")

    // アイテムのレアリティ
    // コモン | アンコモン | レア | エピック | レジェンダリー | ミシック | 古代
    val rarity = text("rarity")

    // アイテムのカテゴリ
    // 武器 | 防具 | 素材 | 道具 | ポーション | 消費アイテム
    val category = text("category")

    // エンチャントスロット
    val enchantSlot = integer("enchant_slot")

    // アイテムの売値
    val sellPrice = integer("sell_price")

    // アイテムが取引可能か
    val tradable = bool("tradable")

    // ----- オプション -----
    // 数字と%を指定できる

    // 攻撃力
    val damage = text("damage")

    // 防御力
    val defence = text("defence")

    // 体力増加
    val health = text("health")

    // マナ増加
    val mana = text("mana")

    // クリティカル確率増加
    val criticalDamage = text("critical_damage")

    // クリティカル倍率
    val criticalPercent = text("critical_percent")

}