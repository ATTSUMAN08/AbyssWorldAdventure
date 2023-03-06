package me.attsuman08.rpg.listener

import me.attsuman08.rpg.Core
import me.attsuman08.rpg.extension.runTaskAsync
import me.attsuman08.rpg.player.PlayerUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import kotlin.math.floor

class MainListener : Listener {

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

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        val p: Player = e.player
        if(e.action == Action.RIGHT_CLICK_BLOCK) {
            // OPを所持していない場合イベントをキャンセル
            when (e.clickedBlock!!.type) {
                Material.CHEST, Material.TRAPPED_CHEST, Material.CRAFTING_TABLE, Material.STONECUTTER, Material.CARTOGRAPHY_TABLE, Material.FLETCHING_TABLE,
                Material.SMITHING_TABLE, Material.GRINDSTONE, Material.LOOM, Material.FURNACE, Material.SMOKER, Material.BLAST_FURNACE, Material.CAMPFIRE,
                Material.SOUL_CAMPFIRE, Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL, Material.COMPOSTER, Material.NOTE_BLOCK,
                Material.JUKEBOX, Material.ENCHANTING_TABLE, Material.BREWING_STAND, Material.CAULDRON, Material.BEACON, Material.LODESTONE,
                Material.RESPAWN_ANCHOR, Material.BARREL -> e.isCancelled = !p.isOp
                else -> {}
            }
        }
    }

}