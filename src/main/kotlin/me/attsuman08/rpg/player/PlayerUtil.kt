package me.attsuman08.rpg.player

import me.attsuman08.rpg.Core
import org.bukkit.entity.Player

class PlayerUtil(val p: Player) {
    private val pStorage = Core.PLAYER_DATA_STORAGE[p]!!

    fun damage(amount: Int) {
        pStorage.health = pStorage.health-amount
        if(pStorage.health <= 0) {
            p.sendTitle("YOU ARE DEAD", "", 0, 40, 20)
            pStorage.health = pStorage.maxHealth
            p.health = 20.0
            p.teleportAsync(Core.RESPAWN_LOCATION)
            return
        }
        p.health = (pStorage.health.toDouble()/pStorage.maxHealth.toDouble())*20
    }

    fun heal(amount: Int) {
        pStorage.health = pStorage.health+amount
        if(pStorage.health > pStorage.maxHealth) {
            pStorage.health = pStorage.maxHealth
        }
        p.health = (pStorage.health.toDouble()/pStorage.maxHealth.toDouble())*20
    }

}