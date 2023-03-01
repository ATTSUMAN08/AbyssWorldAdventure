package me.attsuman08.rpg

import me.attsuman08.rpg.extension.runTaskAsync
import me.attsuman08.rpg.player.PlayerUtil
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import kotlin.math.floor

class Events : Listener {

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p: Player = e.player
        runTaskAsync {
            // プレイヤーデータを読み込み
            Core.DATABASE.loadPlayerData(p)
        }
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        val p: Player = e.player
        runTaskAsync {
            // プレイヤーデータを保存
            Core.DATABASE.savePlayerData(p)
        }
    }

    @EventHandler
    fun onHungerChange(e: FoodLevelChangeEvent) {
        e.foodLevel = 20
    }

    @EventHandler
    fun onDamage(e: EntityDamageEvent) {
        if(e.entity is Player) {
            val damage = floor(e.damage).toInt()
            e.damage = 0.0
            PlayerUtil(e.entity as Player).damage(damage)
        }
    }

}