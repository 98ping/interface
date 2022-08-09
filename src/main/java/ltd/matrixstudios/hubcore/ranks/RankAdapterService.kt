package ltd.matrixstudios.hubcore.ranks

import ltd.matrixstudios.alchemist.api.AlchemistAPI
import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.ranks.impl.AlchemistAdapter
import ltd.matrixstudios.hubcore.services.Service
import org.bukkit.entity.Player
import java.util.logging.Level

object RankAdapterService : Service
{
    val PLUGIN_NAMES = listOf<Pair<String, RankAdapter>>(Pair("Alchemist", AlchemistAdapter()))

    var rankAdapter: RankAdapter? = null

    fun setupRankAdapter()
    {
        val startingTime = System.currentTimeMillis()
        val firstAdapter = PLUGIN_NAMES.firstOrNull {
            InterfacePlugin.instance.server.pluginManager.isPluginEnabled(it.first)
        }

        if (firstAdapter != null)
        {
            rankAdapter = firstAdapter.second
        }

        InterfacePlugin.instance.logger.log(Level.INFO, "[ranks] Rank adapter lookup took ${System.currentTimeMillis().minus(startingTime)} milliseconds")
    }

    fun getRankDisplay(player: Player) : String {
        if (rankAdapter != null)
        {
            return rankAdapter!!.getRankForPlayer(player)
        }

        return "&fUnknown"
    }

    override fun initiate() {
        setupRankAdapter()
    }
}