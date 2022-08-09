package ltd.matrixstudios.hubcore

import co.aikar.commands.PaperCommandManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import io.github.thatkawaiisam.assemble.Assemble
import io.github.thatkawaiisam.assemble.AssembleStyle
import ltd.matrixstudios.hubcore.commands.InterfaceCommands
import ltd.matrixstudios.hubcore.displays.HubcoreScoreboard
import ltd.matrixstudios.hubcore.inventory.InventoryLoadoutService
import ltd.matrixstudios.hubcore.menus.CustomMenuService
import ltd.matrixstudios.hubcore.ranks.RankAdapterService
import ltd.matrixstudios.hubcore.selector.SelectorItemService
import ltd.matrixstudios.hubcore.utils.menu.listener.MenuListener
import me.lucko.helper.Events
import me.lucko.helper.plugin.ExtendedJavaPlugin
import me.lucko.helper.plugin.ap.Plugin
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
        .setPrettyPrinting()
        .create()

    override fun enable() {
        instance = this

        saveDefaultConfig()
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
        InventoryLoadoutService.initiate()
        CustomMenuService.initiate()
    }

    fun registerEvents()
    {
        Events.subscribe(
            PlayerJoinEvent::class.java
        ).handler {

        }
    }

    fun registerMenuAPI()
    {
        server.pluginManager.registerEvents(MenuListener(), this)
    }

}