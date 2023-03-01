package me.attsuman08.rpg.multithread

import me.attsuman08.rpg.Core.Companion.PLUGIN
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

class MultiThread : Thread() {
    companion object {
        var bukkitTaskTag: HashMap<String, BukkitTask> = HashMap()

        fun sleepTick(tick: Long) {
            try {
                sleep(tick * 50)
            } catch (ignored: InterruptedException) {
            }
        }

        fun taskRunAsyncTimer(runnable: () -> Unit, tick: Int, threadTag: String) {
            if (PLUGIN.isEnabled) {
                try {
                    bukkitTaskTag[threadTag] = Bukkit.getScheduler().runTaskTimerAsynchronously(PLUGIN, runnable, 0, tick.toLong())
                } catch (e: Exception) {
                    e.printStackTrace()
                    PLUGIN.logger.warning("タスク実行に失敗しました Task -> $threadTag")
                }
            }
        }

        fun taskRunAsyncLater(runnable: () -> Unit, tick: Int, threadTag: String) {
            if (PLUGIN.isEnabled) {
                try {
                    bukkitTaskTag[threadTag] = Bukkit.getScheduler().runTaskLaterAsynchronously(
                        PLUGIN,
                        runnable, tick.toLong()
                    )
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    PLUGIN.logger.warning("タスク実行に失敗しました Task -> $threadTag")
                }
            }
        }

        fun taskRunAsync(runnable: () -> Unit, threadTag: String) {
            if (PLUGIN.isEnabled) {
                try {
                    bukkitTaskTag[threadTag] = Bukkit.getScheduler().runTaskAsynchronously(
                        PLUGIN,
                        runnable
                    )
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    PLUGIN.logger.warning("タスク実行に失敗しました Task -> $threadTag")
                }
            }
        }

        fun taskRun(runnable: () -> Unit, threadTag: String) {
            if (PLUGIN.isEnabled) {
                try {
                    bukkitTaskTag[threadTag] = Bukkit.getScheduler().runTask(
                        PLUGIN,
                        runnable
                    )
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    PLUGIN.logger.warning("タスク実行に失敗しました Task -> $threadTag")
                }
            }
        }
    }
}