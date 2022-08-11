package ltd.matrixstudios.hubcore.queues

import org.bukkit.entity.Player

interface QueuePlugin {

    fun getPosition(player: Player) : Int
    fun getTotalInQueue(player: Player) : Int
    fun isQueued(player: Player) : Boolean
    fun getQueuedServerName(player: Player) : String
    
}