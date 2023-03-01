package me.attsuman08.rpg.extension

import me.attsuman08.rpg.Core
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

/**
 * [BukkitRunnable.runTask]の簡略化版
 *
 * @param action 処理
 * @return task BukkitTask
 */
fun runTask(action: () -> Unit): BukkitTask {
    return object : BukkitRunnable() {
        override fun run() {
            action()
        }
    }.runTask(Core.PLUGIN)
}

/**
 * [BukkitRunnable.runTaskLater]の簡略化版
 *
 * @param delay 最初の遅延時間(ticks)
 * @param action 処理
 * @return task BukkitTask
 */
fun runTaskLater(delay: Long, action: () -> Unit): BukkitTask {
    return object : BukkitRunnable() {
        override fun run() {
            action()
        }
    }.runTaskLater(Core.PLUGIN, delay)
}

/**
 * [BukkitRunnable.runTaskTimer]の簡略化版
 *
 * @param delay 最初の遅延時間(ticks)
 * @param period 繰り返し間隔(ticks)
 * @param action 処理 引数:実行番号(0から) 返り値: false->以降のタスクがキャンセル
 * @return task BukkitTask
 */
fun runTaskTimer(delay: Long, period: Long, action: (Long) -> Boolean): BukkitTask {
    var count = 0L
    return object : BukkitRunnable() {
        override fun run() {
            if (!action(count++)) cancel()
        }
    }.runTaskTimer(Core.PLUGIN, delay, period)
}

/**
 * [BukkitRunnable.runTaskAsynchronously]の簡略化版
 *
 * @param action 処理
 * @return task BukkitTask
 */
fun runTaskAsync(action: () -> Unit): BukkitTask {
    return object : BukkitRunnable() {
        override fun run() {
            action()
        }
    }.runTaskAsynchronously(Core.PLUGIN)
}

/**
 * [BukkitRunnable.runTaskLaterAsynchronously]の簡略化版
 *
 * @param delay 最初の遅延時間(ticks)
 * @param action 処理
 * @return task BukkitTask
 */
fun runTaskLaterAsync(delay: Long, action: () -> Unit): BukkitTask {
    return object : BukkitRunnable() {
        override fun run() {
            action()
        }
    }.runTaskLaterAsynchronously(Core.PLUGIN, delay)
}

/**
 * [BukkitRunnable.runTaskTimerAsynchronously]の簡略化版
 *
 * @param delay 最初の遅延時間(ticks)
 * @param period 繰り返し間隔(ticks)
 * @param action 処理 引数:実行番号(0から) 返り値: false->以降のタスクがキャンセル
 * @return task BukkitTask
 */
fun runTaskTimerAsync(delay: Long, period: Long, action: (Long) -> Boolean): BukkitTask {
    var count = 0L
    return object : BukkitRunnable() {
        override fun run() {
            if (!action(count++)) cancel()
        }
    }.runTaskTimerAsynchronously(Core.PLUGIN, delay, period)
}