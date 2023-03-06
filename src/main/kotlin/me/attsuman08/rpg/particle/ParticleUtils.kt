package me.attsuman08.rpg.particle

import me.attsuman08.rpg.extension.runTaskAsync
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import kotlin.math.cos
import kotlin.math.sin

class ParticleUtils {

    private fun isAIR(block: Material): Boolean {
        return when(block) {
            Material.AIR, Material.GRASS, Material.TALL_GRASS, Material.GLASS, Material.GLASS_PANE, Material.BARRIER, Material.WATER,
            Material.OAK_LEAVES -> true
            else -> false
        }
    }

    fun spawnCircleParticles(center: Location, particle: Particle, radius: Double, count: Int) {
        runTaskAsync {
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

    fun createShoot(attacker: Player, particle: Particle, range: Int, damage: Double) {
        var loc = attacker.location
        loc = loc.add(0.0, 1.5, 0.0)
        for (i in 1..range step 1) {
            loc = loc.add(attacker.eyeLocation.direction)
            if(!isAIR(loc.block.type)) return
            attacker.world.spawnParticle(particle, loc, 2, 0.0, 0.0, 0.0, 0.0)
            val list: List<LivingEntity> =
                attacker.world.getNearbyLivingEntities(loc, 1.0,1.0, 1.0) as List<LivingEntity>
            for (e in list) {
                if(e.type == EntityType.PLAYER) continue
                if(e.type == EntityType.ITEM_FRAME) continue
                if(e.type == EntityType.ARMOR_STAND) continue
                //damage(attacker, e, damage)
            }
        }
    }


}