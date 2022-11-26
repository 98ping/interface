package ltd.matrixstudios.hubcore.proxy

import com.google.common.collect.Iterables
import com.google.common.io.ByteStreams
import ltd.matrixstudios.hubcore.InterfacePlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import org.bukkit.scheduler.BukkitRunnable
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
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

        object : BukkitRunnable()
        {
            override fun run() {
                servers.forEach(Consumer { server: String ->
                    getPlayerCount(
                        server
                    )
                })
            }
        }.runTaskTimerAsynchronously(InterfacePlugin.instance, 20L, 20L)
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

    fun send(player: Player, server: String?) {
        val b = ByteArrayOutputStream()
        val out = DataOutputStream(b)
        try {
            out.writeUTF("Connect")
            out.writeUTF(server)
        } catch (ignored: IOException) {
        }
        player.sendPluginMessage(InterfacePlugin.instance, "BungeeCord", b.toByteArray())
    }

    fun sendAll(server: String?) {
        val b = ByteArrayOutputStream()
        val out = DataOutputStream(b)
        try {
            out.writeUTF("Connect")
            out.writeUTF(server)
        } catch (ignored: IOException) {
        }
        for (player in Bukkit.getOnlinePlayers()) {
            player.sendPluginMessage(InterfacePlugin.instance, "BungeeCord", b.toByteArray())
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
