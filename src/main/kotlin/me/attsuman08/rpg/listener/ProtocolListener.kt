package me.attsuman08.rpg.listener

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import me.attsuman08.rpg.Core
import org.bukkit.Particle


class ProtocolListener {

    fun disableBlackHeart() {
        ProtocolLibrary.getProtocolManager().addPacketListener(object :
            PacketAdapter(Core.PLUGIN, ListenerPriority.HIGH, PacketType.Play.Server.WORLD_PARTICLES) {
            override fun onPacketSending(event: PacketEvent) {
                val packet = event.packet
                if (event.packetType !== PacketType.Play.Server.WORLD_PARTICLES) return
                if (packet.newParticles.read(0).particle == Particle.DAMAGE_INDICATOR) event.isCancelled = true
            }
        })
    }
}