package ltd.matrixstudios.hubcore.cosmetics

import gg.scala.store.storage.type.DataStoreStorageType
import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.services.Service
import ltd.matrixstudios.hubcore.users.UserService
import ltd.matrixstudios.hubcore.utils.ItemBuilder
import me.lucko.helper.Events
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import java.awt.Color
import kotlin.math.cos

object CosmeticService : Service
{
    var cosmetics = hashMapOf<String, Cosmetic>()

    fun getCosmeticsByType(cosmeticType: CosmeticType) : MutableList<Cosmetic>
    {
        return cosmetics.values.filter { it.type == cosmeticType }.toMutableList()
    }

    fun loadCosmetics()
    {
        val config = InterfacePlugin.instance.config

        for (item in config.getConfigurationSection("cosmetics").getKeys(false))
        {
            val id = item
            val displayName = config.getString("cosmetics.$item.displayName")
            val displayColor = config.getString("cosmetics.$item.color")
            val displayItem = config.getString("cosmetics.$item.item")
            val displayData = config.getInt("cosmetics.$item.data")
            val cosmeticType = config.getString("cosmetics.$item.type")
            val permission = config.getString("cosmetics.$item.permission")

            cosmetics[id] = Cosmetic(
                id,
                displayName,
                Color.getColor(displayColor),
                Material.getMaterial(displayItem),
                CosmeticType.valueOf(cosmeticType),
                displayData.toShort(),
                permission
            )

        }
    }

    fun applyCosmetic(player: Player, cosmetic: Cosmetic)
    {
        val colorRGB = cosmetic.color.rgb

        val bukkitColor = org.bukkit.Color.fromRGB(colorRGB)
        val armor = arrayOf(
            ItemBuilder.of(Material.LEATHER_HELMET).color(bukkitColor).build(),
            ItemBuilder.of(Material.LEATHER_CHESTPLATE).color(bukkitColor).build(),
            ItemBuilder.of(Material.LEATHER_LEGGINGS).color(bukkitColor).build(),
            ItemBuilder.of(Material.LEATHER_BOOTS).color(bukkitColor).build()
        )

        player.inventory.armorContents = armor

        player.updateInventory()

        val userProfile = UserService.controller[player.uniqueId]

        if (userProfile != null)
        {
            userProfile.activeArmor = cosmetic.id
            UserService.controller.save(userProfile, DataStoreStorageType.MONGO)

            UserService.controller.localCache[player.uniqueId] = userProfile
        }

    }

    fun loadEvents()
    {
        Events.subscribe(PlayerJoinEvent::class.java).handler {
            val player = it.player

            val userProfile = UserService.controller[player.uniqueId]

            if (userProfile != null)
            {
                val activeArmor = userProfile.activeArmor

                val armorFromService = cosmetics.getOrDefault(activeArmor, null)

                if (armorFromService != null)
                {
                    applyCosmetic(player, armorFromService)
                }
            }
        }
    }

    override fun initiate() {
        loadCosmetics()
        loadEvents()
    }
}