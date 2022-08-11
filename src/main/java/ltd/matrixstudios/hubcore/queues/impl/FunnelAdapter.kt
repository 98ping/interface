package ltd.matrixstudios.hubcore.queues.impl

import ltd.matrixstudios.application.queues.QueueService
import ltd.matrixstudios.hubcore.queues.QueuePlugin
import org.bukkit.entity.Player

class FunnelAdapter : QueuePlugin {

    override fun getPosition(player: Player): Int {
        return QueueService.findQueueByPlayer(player.uniqueId).get()!!.getPosition(player.uniqueId)
    }

    override fun getTotalInQueue(player: Player): Int {
        return QueueService.findQueueByPlayer(player.uniqueId).get()!!.getTotalPlayersQueued()
    }

    override fun isQueued(player: Player): Boolean {
        return QueueService.findQueueByPlayer(player.uniqueId).get() != null
    }

    override fun getQueuedServerName(player: Player): String {
        return QueueService.findQueueByPlayer(player.uniqueId).get()!!.fancyName
    }
}