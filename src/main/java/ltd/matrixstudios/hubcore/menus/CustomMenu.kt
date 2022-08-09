package ltd.matrixstudios.hubcore.menus

import com.google.common.io.Files

class CustomMenu(var id: String)
{
    var title = "Menu"
    var size = 27
    var buttons = mutableMapOf<Int, CustomMenuButton>()
}
