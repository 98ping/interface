package ltd.matrixstudios.hubcore.ranks.impl

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.alchemist.service.profiles.ProfileGameService
import ltd.matrixstudios.hubcore.ranks.RankAdapter
import org.bukkit.entity.Player

class AlchemistAdapter : RankAdapter
{

    override fun getRankForPlayer(player: Player): String
    {
        val rank = ProfileGameService.byId(player.uniqueId)!!.getCurrentRank()!!

        return rank.color + rank.displayName
    }
}