package me.attsuman08.rpg.database.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object PlayerData : Table() {
    val mcid = text("mcid")

    val uuid = uuid("uuid")
    override val primaryKey = PrimaryKey(uuid)

    // 最終ログイン
    val lastLogin = datetime("last_login")

    // 接続に使用したIPアドレス一覧
    val addressList = text("address_list")

    // 冒険者ランク
    val rank = text("rank")
}