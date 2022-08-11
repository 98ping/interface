package ltd.matrixstudios.hubcore.cosmetics.menu

import ltd.matrixstudios.hubcore.cosmetics.Cosmetic
import ltd.matrixstudios.hubcore.cosmetics.CosmeticService
import ltd.matrixstudios.hubcore.cosmetics.CosmeticType
import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.menu.Button
import ltd.matrixstudios.hubcore.utils.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import kotlin.math.cos

class CosmeticCategoryMenu(val player: Player, val cosmeticType: CosmeticType) : PaginatedMenu(18, player)
{
    override fun getPagesButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var i = 0

        for (cosmetic in CosmeticService.getCosmeticsByType(cosmeticType))
        {
            buttons[i++] = CosmeticButton(cosmetic)
        }

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Viewing: ${cosmeticType.name}"
    }

    class CosmeticButton(val cosmetic: Cosmetic) : Button()
    {
        override fun getMaterial(player: Player): Material {
            return cosmetic.displayItem
        }

        override fun getDescription(player: Player): MutableList<String>? {
            return mutableListOf()
        }

        override fun getDisplayName(player: Player): String? {
            return Chat.format(cosmetic.displayName)
        }

        override fun getData(player: Player): Short {
            return cosmetic.data
        }

        override fun onClick(player: Player, slot: Int, type: ClickType) {
            if (player.hasPermission(cosmetic.permission))
            {
                CosmeticService.applyCosmetic(player, cosmetic)
                player.sendMessage(Chat.format("&aEquipped this Cosmetic!"))
            } else player.sendMessage(Chat.format("&cYou do not have access to this cosmetic!"))
        }

    }
}