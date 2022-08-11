package ltd.matrixstudios.hubcore.queues

import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.queues.impl.FunnelAdapter
import ltd.matrixstudios.hubcore.ranks.RankAdapter
import ltd.matrixstudios.hubcore.ranks.impl.AlchemistAdapter
import ltd.matrixstudios.hubcore.services.Service
import org.bukkit.entity.Player
import java.util.logging.Level

object QueuePluginService : Service
{
    val PLUGIN_NAMES = listOf<Pair<String, QueuePlugin>>(Pair("Funnel", FunnelAdapter()))

    var queueAdapter: QueuePlugin? = null

    fun setupQueue()
    {
        val startingTime = System.currentTimeMillis()
        val firstAdapter = PLUGIN_NAMES.firstOrNull {
            InterfacePlugin.instance.server.pluginManager.isPluginEnabled(it.first)
        }

        if (firstAdapter != null)
        {
           queueAdapter = firstAdapter.second
        }

        InterfacePlugin.instance.logger.log(Level.INFO, "[queues] Queue plugin lookup took ${System.currentTimeMillis().minus(startingTime)} milliseconds")
    }


    override fun initiate() {
         setupQueue()
    }
}