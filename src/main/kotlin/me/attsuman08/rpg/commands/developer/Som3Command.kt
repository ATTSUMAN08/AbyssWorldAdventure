package me.attsuman08.rpg.commands.developer

import me.attsuman08.rpg.commands.AbyssCommand
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class Som3Command : AbyssCommand {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        sender.sendMessage("${ChatColor.GREEN}SOM3モードを有効化しました！")
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        return null
    }
}