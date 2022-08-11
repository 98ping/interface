package ltd.matrixstudios.hubcore.cosmetics.menu

import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.cosmetics.CosmeticType
import ltd.matrixstudios.hubcore.utils.Chat
import ltd.matrixstudios.hubcore.utils.ItemBuilder
import ltd.matrixstudios.hubcore.utils.menu.Button
import ltd.matrixstudios.hubcore.utils.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class GeneralCosmeticMenu(val player: Player) : Menu(9, player)
{
    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        buttons[3] = CosmeticCategoryButton(CosmeticType.ARMOR)
        buttons[5] = CosmeticCategoryButton(CosmeticType.PARTICLE)

        return buttons
    }

    override fun getTitle(player: Player): String {
        return "Select a category"
    }

    class CosmeticCategoryButton(val category: CosmeticType) : Button()
    {
        val config = InterfacePlugin.instance.config

        override fun getMaterial(player: Player): Material {
            val particle = config.getString("cosmeticButtons.particles.item")
            val armor = config.getString("cosmeticButtons.armor.item")

            if (category == CosmeticType.ARMOR)
            {
                return Material.getMaterial(armor)
            } else return Material.getMaterial(particle)
        }

        override fun getDescription(player: Player): MutableList<String>? {
            val particle = config.getStringList("cosmeticButtons.particles.lore")
            val armor = config.getStringList("cosmeticButtons.armor.lore")

            if (category == CosmeticType.ARMOR)
            {
                return armor.map { Chat.format(it) }.toMutableList()
            } else return particle.map { Chat.format(it) }.toMutableList()
        }

        override fun getDisplayName(player: Player): String? {
            val particle = config.getString("cosmeticButtons.particles.name")
            val armor = config.getString("cosmeticButtons.armor.name")

            if (category == CosmeticType.ARMOR)
            {
                return Chat.format(armor)
            } else return Chat.format(particle)
        }

        override fun getData(player: Player): Short {
            val particle = config.getInt("cosmeticButtons.particles.data")
            val armor = config.getInt("cosmeticButtons.armor.data")

            if (category == CosmeticType.ARMOR)
            {
                return armor.toShort()
            } else return particle.toShort()
        }


        override fun onClick(player: Player, slot: Int, type: ClickType) {
            CosmeticCategoryMenu(player, category).updateMenu()
        }

    }
}