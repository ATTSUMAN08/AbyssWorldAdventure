package me.attsuman08.rpg.player

import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.entity.Player

enum class Rank {
    NOVICE {
        override fun setRank(p: Player) {
            p.playerListName(Component.text("${ChatColor.GRAY}${ChatColor.BOLD}[鈴付き] ${p.name}"))
        }
    },
    RED {
        override fun setRank(p: Player) {
            p.playerListName(Component.text("${ChatColor.RED}${ChatColor.BOLD}[赤笛] ${p.name}"))
        }
    },
    BLUE {
        override fun setRank(p: Player) {
            p.playerListName(Component.text("${ChatColor.BLUE}${ChatColor.BOLD}[蒼笛] ${p.name}"))
        }
    },
    PURPLE {
        override fun setRank(p: Player) {
            p.playerListName(Component.text("${ChatColor.DARK_PURPLE}${ChatColor.BOLD}[月笛] ${p.name}"))
        }
    },
    BLACK {
        override fun setRank(p: Player) {
            p.playerListName(Component.text("${ChatColor.BLACK}${ChatColor.BOLD}[黒笛] ${p.name}"))
        }
    },
    WHITE {
        override fun setRank(p: Player) {
            //p.playerListName(Component.text("${ChatColor.WHITE}${ChatColor.BOLD}[白笛] ${p.name}"))
            p.playerListName(Component.text("${ChatColor.AQUA}[MVP+] ${p.name}"))
        }
    };

    abstract fun setRank(p: Player)

}