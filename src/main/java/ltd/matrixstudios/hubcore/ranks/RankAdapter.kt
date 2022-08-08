package ltd.matrixstudios.hubcore.ranks

import org.bukkit.entity.Player

interface RankAdapter {

    fun getRankForPlayer(player: Player) : String
}