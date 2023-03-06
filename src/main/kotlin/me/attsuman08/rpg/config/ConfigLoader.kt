package me.attsuman08.rpg.config

import me.attsuman08.rpg.Core
import java.io.File

class ConfigLoader {
    fun init() {
        // config.ymlの初期化
        val dataFolder: File = Core.PLUGIN.dataFolder
        if(!dataFolder.exists()) dataFolder.mkdir()
        Core.PLUGIN.saveDefaultConfig()
        Core.PLUGIN.reloadConfig()
    }

}