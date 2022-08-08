package ltd.matrixstudios.hubcore.proxy

import com.google.common.collect.Iterables
import com.google.common.io.ByteStreams
import ltd.matrixstudios.hubcore.InterfacePlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

//stolen from my other hubcore. This class looks absolutely horrific do not look at it please for the love of god
object ProxyUtils : PluginMessageListener {
    private val playerCounts: MutableMap<String, Int> = ConcurrentHashMap()

    fun getPlayerCounts(): Map<String, Int> {
        return playerCounts
    }

    private val servers = listOf("ALL") //could be used for individual servers but I CBA rn

    fun load() {
        Bukkit.getServer().messenger.registerOutgoingPluginChannel(InterfacePlugin.instance, "BungeeCord")
        Bukkit.getServer().messenger.registerIncomingPluginChannel(InterfacePlugin.instance, "BungeeCord", this)

        Bukkit.getScheduler()
            .runTaskTimerAsynchronously(InterfacePlugin.instance,
            {
                servers.forEach(Consumer { server: String ->
                    getPlayerCount(
                        server
                    )
                })
            }, 20L, 20L
        )
    }

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (!channel.equals("BungeeCord", ignoreCase = true)) return

        val `in` = ByteStreams.newDataInput(message)
        val subchannel = `in`.readUTF()

        if (subchannel.equals("PlayerCount", ignoreCase = true)) try
        {
            val server = `in`.readUTF()
            val count = `in`.readInt()
            if (servers.contains(server))
            {
                playerCounts[server] = Integer.valueOf(count)
            }
        } catch (exception: Exception)
        {
            return
        }
    }

    fun getPlayerCount(server: String)
    {
        val out = ByteStreams.newDataOutput()
        out.writeUTF("PlayerCount")
        out.writeUTF(server)

        Iterables.getFirst(Bukkit.getOnlinePlayers(), null)?.sendPluginMessage(InterfacePlugin.instance, "BungeeCord", out.toByteArray())
    }
}
