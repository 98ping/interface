package ltd.matrixstudios.hubcore.cosmetics

import org.bukkit.Material
import java.awt.Color

class Cosmetic(
    var id: String,
    var displayName: String,
    var color: Color,
    var displayItem: Material,
    var type: CosmeticType,
    var data: Short,
    var permission: String
) {
}