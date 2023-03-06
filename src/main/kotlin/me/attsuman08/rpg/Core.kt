package me.attsuman08.rpg

import me.attsuman08.rpg.commands.AbyssCommand
import me.attsuman08.rpg.commands.developer.DatabaseCommand
import me.attsuman08.rpg.commands.developer.Som3Command
import me.attsuman08.rpg.config.ConfigLoader
import me.attsuman08.rpg.database.MySQL
import me.attsuman08.rpg.extension.runTaskAsync
import me.attsuman08.rpg.extension.runTaskTimerAsync
import me.attsuman08.rpg.listener.MainListener
import me.attsuman08.rpg.listener.ProtocolListener
import me.attsuman08.rpg.listener.SoundListener
import me.attsuman08.rpg.player.PlayerStorage
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.ResultRow


class Core : JavaPlugin() {
    companion object {
        lateinit var PLUGIN: Core
            private set
        lateinit var DATABASE: MySQL
            private set
        val PLAYER_DATA = HashMap<Player, ResultRow>()
        val PLAYER_DATA_STORAGE = HashMap<Player, PlayerStorage>()
        val RESPAWN_LOCATION = Location(Bukkit.getWorld("world"), 0.5, 1801.0, 0.5, -180F, 0F)

        fun commandRegister(cmd: String, cmdClass: AbyssCommand) {
            Bukkit.getPluginCommand(cmd)!!.setExecutor(cmdClass)
            Bukkit.getPluginCommand(cmd)!!.tabCompleter = cmdClass
        }
    }

    override fun onEnable() {
        PLUGIN = this
        DATABASE = MySQL()
        ConfigLoader().init()
        Bukkit.getPluginManager().registerEvents(MainListener(), this)
        Bukkit.getPluginManager().registerEvents(SoundListener(), this)
        ProtocolListener().disableBlackHeart()
        DATABASE.connect()
        for (world in Bukkit.getWorlds()) {
            world.setGameRule(GameRule.DROWNING_DAMAGE, false)
            world.setGameRule(GameRule.FALL_DAMAGE, false)
            world.setGameRule(GameRule.FIRE_DAMAGE, false)
            world.setGameRule(GameRule.FREEZE_DAMAGE, false)
            world.setGameRule(GameRule.DROWNING_DAMAGE, false)
            world.setGameRule(GameRule.KEEP_INVENTORY, true)
            world.setGameRule(GameRule.NATURAL_REGENERATION, false)
            world.setGameRule(GameRule.DO_INSOMNIA, false)
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false)
            world.setGameRule(GameRule.DO_WARDEN_SPAWNING, false)
            world.setGameRule(GameRule.DO_MOB_LOOT, false)
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false)
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0)
            world.setGameRule(GameRule.MOB_GRIEFING, false)
        }

        runTaskAsync {
            commandRegister("database", DatabaseCommand())
            commandRegister("som3", Som3Command())

            for (p in Bukkit.getOnlinePlayers()) {
                DATABASE.loadPlayerData(p)
            }
        }

        runTaskTimerAsync(100, 24000) {
            for (p in Bukkit.getOnlinePlayers()) {
                DATABASE.savePlayerData(p)
            }
            return@runTaskTimerAsync true
        }

        runTaskTimerAsync(10, 5) {
            for (p in Bukkit.getOnlinePlayers()) {
                val pStorage = PLAYER_DATA_STORAGE[p]
                if(pStorage != null) {
                    p.sendActionBar(Component.text("§c体力 ${pStorage.health}/${pStorage.maxHealth}"))
                }
            }
            return@runTaskTimerAsync true
        }
    }

    override fun onDisable() {
    }

}