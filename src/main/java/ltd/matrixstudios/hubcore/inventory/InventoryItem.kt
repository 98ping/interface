package ltd.matrixstudios.hubcore.inventory

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class InventoryItem(
    var itemStack: ItemStack,
    var usesAction: Boolean,
    var commands: MutableList<String>
)