package me.attsuman08.rpg.commands.developer

import me.attsuman08.rpg.Core
import me.attsuman08.rpg.commands.AbyssCommand
import me.attsuman08.rpg.database.table.PlayerData
import me.attsuman08.rpg.extension.runTaskAsync
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*


class DatabaseCommand : AbyssCommand {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if(args.isNullOrEmpty()) {
            sendHelp(sender)
            return true
        }
        if(sender !is Player) {
            sender.sendMessage("${ChatColor.RED}このコマンドはプレイヤー専用です")
            return true
        }

        sender.sendMessage(args.size.toString())
        if(args.size >= 1) {
            if (args[0].equals("load", ignoreCase = true)) {
                Core.DATABASE.loadPlayerData(sender)
            } else if (args[0].equals("save", ignoreCase = true)) {
                Core.DATABASE.savePlayerData(sender)
            }
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
        return null
    }

    private fun sendHelp(sender: CommandSender) {
        sender.sendMessage("/database load")
        sender.sendMessage("/database save")
    }

}