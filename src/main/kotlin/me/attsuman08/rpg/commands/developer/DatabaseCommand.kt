package me.attsuman08.rpg.commands.developer

import me.attsuman08.rpg.Core
import me.attsuman08.rpg.particle.ParticleUtils
import me.attsuman08.rpg.commands.AbyssCommand
import org.bukkit.ChatColor
import org.bukkit.Particle
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


class DatabaseCommand : AbyssCommand {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}このコマンドはプレイヤー専用です")
            return true
        }
        if (args.isNullOrEmpty() || args.isEmpty()) {
            sendHelp(sender)
            return true
        }
        when(args[0].lowercase()) {
            "load" -> {
                Core.DATABASE.loadPlayerData(sender)
            }
            "save" -> {
                Core.DATABASE.savePlayerData(sender)
            }
            "test" -> {
                ParticleUtils().spawnCircleParticles(sender.location, Particle.SPELL_WITCH, 5.0, 100)
            }
            "test2" -> {
                ParticleUtils().createShoot(sender, Particle.SPELL_WITCH, 5, 5.0)
            }
            else -> sendHelp(sender)
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String> {
        val list = ArrayList<String>()
        val argsSize = args!!.size
        if(argsSize == 1) {
            list.add("load")
            list.add("save")
            list.add("test")
        }
        return list
    }

    private fun sendHelp(sender: CommandSender) {
        sender.sendMessage("=====[Command Help]=====")
        sender.sendMessage("/database load")
        sender.sendMessage("/database save")
    }

}