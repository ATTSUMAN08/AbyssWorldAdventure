package me.attsuman08.rpg.listener

import com.craftmend.openaudiomc.api.interfaces.AudioApi
import com.craftmend.openaudiomc.generic.media.objects.MediaOptions
import me.attsuman08.rpg.Core
import me.attsuman08.rpg.extension.runTaskLaterAsync
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent


class SoundListener : Listener {

    @EventHandler
    fun onDamageByEntity(e: EntityDamageByEntityEvent) {
        val attacker = e.damager
        val victim = e.entity
        val api = AudioApi.getInstance()
        val option = MediaOptions()
        option.id = "ATTACK"
        option.volume = 60
        option.isPickUp = false
        if(attacker.type == EntityType.PLAYER) {
            // ダメージを与えた時
            if(Core.PLAYER_DATA_STORAGE[attacker]!!.damageSoundPlaying) return
            val client = api.getClient(attacker.uniqueId)
            if(!client!!.isConnected) return
            when ((1..3).random()) {
                1 -> api.mediaApi.playMedia(client, Core.PLUGIN.config.getString("Sounds.Attack1"), option)
                2 -> api.mediaApi.playMedia(client, Core.PLUGIN.config.getString("Sounds.Attack2"), option)
                3 -> api.mediaApi.playMedia(client, Core.PLUGIN.config.getString("Sounds.Attack3"), option)
            }
            Core.PLAYER_DATA_STORAGE[attacker]!!.damageSoundPlaying = true
            runTaskLaterAsync(10) {
                Core.PLAYER_DATA_STORAGE[attacker]!!.damageSoundPlaying = false
            }
        } else if(victim.type == EntityType.PLAYER) {
            // ダメージを受けた時
            if(Core.PLAYER_DATA_STORAGE[victim]!!.damageSoundPlaying) return
            val client = api.getClient(victim.uniqueId)
            if(!client!!.isConnected) return
            when ((1..2).random()) {
                1 -> api.mediaApi.playMedia(client, Core.PLUGIN.config.getString("Sounds.Damage1"), option)
                2 -> api.mediaApi.playMedia(client, Core.PLUGIN.config.getString("Sounds.Damage2"), option)
            }
            Core.PLAYER_DATA_STORAGE[victim]!!.damageSoundPlaying = true
            runTaskLaterAsync(10) {
                Core.PLAYER_DATA_STORAGE[victim]!!.damageSoundPlaying = false
            }
        }
    }
}