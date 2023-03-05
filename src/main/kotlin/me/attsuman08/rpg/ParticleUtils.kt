package me.attsuman08.rpg

import org.bukkit.Location
import org.bukkit.Particle
import kotlin.math.cos
import kotlin.math.sin

class ParticleUtils {

    fun spawnCircleParticles(center: Location, particle: Particle, radius: Double, count: Int) {
        val world = center.world
        val increment = (2 * Math.PI) / count
        for (i in 0 until count) {
            val angle = i * increment
            val x = center.x + (radius * cos(angle))
            val z = center.z + (radius * sin(angle))
            world.spawnParticle(particle, x, center.y, z, 0)
        }
    }


}