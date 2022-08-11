package ltd.matrixstudios.hubcore

import co.aikar.commands.PaperCommandManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import gg.scala.store.ScalaDataStoreShared
import io.github.thatkawaiisam.assemble.Assemble
import io.github.thatkawaiisam.assemble.AssembleStyle
import ltd.matrixstudios.hubcore.commands.InterfaceCommands
import ltd.matrixstudios.hubcore.cosmetics.CosmeticService
import ltd.matrixstudios.hubcore.cosmetics.commands.CosmeticsCommand
import ltd.matrixstudios.hubcore.displays.HubcoreScoreboard
import ltd.matrixstudios.hubcore.inventory.InventoryLoadoutService
import ltd.matrixstudios.hubcore.location.SpawnLocationManager
import ltd.matrixstudios.hubcore.location.serialize.LocationSerializer
import ltd.matrixstudios.hubcore.menus.CustomMenuService
import ltd.matrixstudios.hubcore.menus.commands.OpenMenuCommand
import ltd.matrixstudios.hubcore.prevention.PreventionListeners
import ltd.matrixstudios.hubcore.proxy.ProxyUtils
import ltd.matrixstudios.hubcore.queues.QueuePlugin
import ltd.matrixstudios.hubcore.queues.QueuePluginService
import ltd.matrixstudios.hubcore.ranks.RankAdapterService
import ltd.matrixstudios.hubcore.selector.SelectorItemService
import ltd.matrixstudios.hubcore.store.DataStoreManager
import ltd.matrixstudios.hubcore.users.UserService
import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.menu.listener.MenuListener
import me.lucko.helper.Events
import me.lucko.helper.plugin.ExtendedJavaPlugin
import me.lucko.helper.plugin.ap.Plugin
import org.bukkit.Location
import org.bukkit.event.player.PlayerJoinEvent


@Plugin(
    name = "interface",
    version = "1.1",
    description = "Light and easy-to-use hubcore with a ton of customization"
)
class InterfacePlugin : ExtendedJavaPlugin()
{

    companion object
    {
        lateinit var instance: InterfacePlugin
    }

    val GSON: Gson = GsonBuilder()
        .serializeNulls()
        .setLongSerializationPolicy(
            LongSerializationPolicy.STRING
        )
        .registerTypeAdapter(Location::class.java, LocationSerializer())
        .setPrettyPrinting()
        .create()

    override fun enable() {
        instance = this

        ScalaDataStoreShared.INSTANCE = DataStoreManager

        saveDefaultConfig()

        ProxyUtils.load()
        registerMenuAPI()
        registerAllSerivces()

        registerEvents()
        initDisplays()
        registerCommands()
    }

    fun registerCommands()
    {
        val commandManager = PaperCommandManager(this).apply {
            this.registerCommand(InterfaceCommands())
            this.registerCommand(OpenMenuCommand())
            this.registerCommand(CosmeticsCommand())
        }
    }

    fun initDisplays()
    {
        val assemble = Assemble(this, HubcoreScoreboard())
        assemble.ticks = 10L
        assemble.assembleStyle = AssembleStyle.MODERN
    }

    fun registerAllSerivces()
    {
        SelectorItemService.initiate()
        RankAdapterService.initiate()
        QueuePluginService.initiate()
        InventoryLoadoutService.initiate()
        CustomMenuService.initiate()
        UserService.initiate()
        SpawnLocationManager.loadSpawnLocation()
        CosmeticService.initiate()
    }

    fun registerEvents()
    {
        PreventionListeners.load()

        Events.subscribe(
            PlayerJoinEvent::class.java
        ).handler { playerJoinEvent ->
            val player = playerJoinEvent.player

            val joinMessages = config.getStringList("joinMessages")

            player.health = 20.0
            player.foodLevel = 10

            joinMessages.forEach {
                player.sendMessage(Chat.format(it))
            }

            if (SpawnLocationManager.spawnLocation != null)
            {
                player.teleport(SpawnLocationManager.spawnLocation)
            }
        }
    }

    fun registerMenuAPI()
    {
        server.pluginManager.registerEvents(MenuListener(), this)
    }

}