package me.attsuman08.rpg.multithread

interface MultiThreadRunnable : Runnable {
    companion object {
        val stackTrace: Array<StackTraceElement> = Throwable().stackTrace
    }
}