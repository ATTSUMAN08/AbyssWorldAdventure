package me.attsuman08.rpg

import java.io.File

class ConfigManager {
    fun init() {
        val dataFolder: File = Core.PLUGIN.dataFolder
        if(!dataFolder.exists()) dataFolder.mkdir()
        Core.PLUGIN.saveDefaultConfig()
        Core.PLUGIN.reloadConfig()
    }

}