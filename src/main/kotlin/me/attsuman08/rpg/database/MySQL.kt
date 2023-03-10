package me.attsuman08.rpg.database

import me.attsuman08.rpg.Core
import me.attsuman08.rpg.database.table.PlayerData
import me.attsuman08.rpg.player.PlayerStorage
import me.attsuman08.rpg.player.Rank
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class MySQL {

    fun connect() {
        val cfg = Core.PLUGIN.config
        TransactionManager.defaultDatabase = Database.connect(
            url = "jdbc:mysql://${cfg.getString("Database.Address")}:${cfg.getString("Database.Port")}/${cfg.getString("Database.Name")}",
            driver = "com.mysql.cj.jdbc.Driver",
            user = cfg.getString("Database.User")!!,
            password = cfg.getString("Database.Password")!!
        )
        transaction {
            // テーブルが存在しない場合テーブルを作成する
            if(!PlayerData.exists()) SchemaUtils.create(PlayerData)
        }
    }

    private fun isPlayerDataExists(p: Player): Boolean {
        var exists = false
        transaction {
            for (it in PlayerData.select { PlayerData.uuid eq p.uniqueId }) {
                exists = true
            }
        }
        return exists
    }

    fun savePlayerData(p: Player) {
        val startMillis = DateTime.now().millis
        transaction {
            val result: ResultRow? = PlayerData.select { PlayerData.uuid eq p.uniqueId }.singleOrNull()
            if (result != null && Core.PLAYER_DATA[p] != null) {
                var address = result[PlayerData.addressList]
                val playerAddress = p.address.address.hostAddress
                if(!address.contains(playerAddress)) {
                    address += playerAddress
                }
                PlayerData.update({ PlayerData.uuid eq p.uniqueId }) {
                    it[mcid] = p.name
                    it[lastLogin] = DateTime.now()
                    it[addressList] = address
                    it[rank] = Core.PLAYER_DATA[p]!![rank]
                }
            } else {
                p.sendMessage("${ChatColor.RED}プレイヤーデータの保存に失敗しました")
                Core.PLUGIN.getLogger().warning("${p.name}のプレイヤーデータの保存に失敗しました")
            }
        }
        p.sendMessage("${ChatColor.GREEN}プレイヤーデータを保存しました ${ChatColor.YELLOW}(${DateTime.now().millis-startMillis}ms)")
    }

    fun loadPlayerData(p: Player) {
        val startMillis = DateTime.now().millis
        // プレイヤーデータが存在しない場合 作成
        if(!isPlayerDataExists(p)) {
            transaction {
                PlayerData.insert {
                    it[mcid] = p.name
                    it[uuid] = p.uniqueId
                    it[lastLogin] = DateTime.now()
                    val list: Array<String> = arrayOf(p.address.address.hostAddress)
                    it[addressList] = list
                    it[rank] = "NOVICE"
                }
            }
        }
        transaction {
            val result = PlayerData.select { PlayerData.uuid eq p.uniqueId }.singleOrNull()
            if (result != null) {
                Core.PLAYER_DATA[p] = result
                Rank.valueOf(result[PlayerData.rank]).setRank(p)
            } else {
                p.sendMessage("${ChatColor.RED}プレイヤーデータの読み込みに失敗しました")
                p.sendMessage("${ChatColor.RED}詳細: プレイヤーデータが存在しません")
                Core.PLUGIN.getLogger().warning("${p.name}のプレイヤーデータの読み込みに失敗しました")
                Core.PLUGIN.getLogger().warning("詳細: プレイヤーデータが存在しません")
            }
        }
        val pStorage = PlayerStorage(p)
        Core.PLAYER_DATA_STORAGE[p] = pStorage
        p.sendMessage("${ChatColor.GREEN}プレイヤーデータを読み込みました ${ChatColor.YELLOW}(${DateTime.now().millis-startMillis}ms)")
    }


}