package me.attsuman08.rpg.player

import org.bukkit.entity.Player

class PlayerStorage(val player: Player) {
    var maxHealth: Int = 100
    var health: Int = 100
}