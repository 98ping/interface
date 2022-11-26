package ltd.matrixstudios.hubcore.menus

import com.google.common.io.Files
import com.google.gson.reflect.TypeToken
import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.services.Service
import java.io.File
import java.lang.reflect.Type

object CustomMenuService : Service
{
    var menus = mutableMapOf<String, CustomMenu>()

    val CONFIG_TYPE: Type = object : TypeToken<MutableList<CustomMenu>>() {}.type

    fun loadMenus()
    {
        val file = File(InterfacePlugin.instance.dataFolder, "customMenus.json")

        if (file.exists())
        {
            val reader = Files.newReader(file, Charsets.UTF_8)

            if (reader != null) {
                InterfacePlugin.instance.GSON.fromJson<
                        MutableList<CustomMenu>
                        >(
                    reader, CONFIG_TYPE
                ).forEach {
                    menus[it.id] = it
                }
            }
        } else
        {
            file.createNewFile()

            if (file.exists())
            {
                Files.write(
                    InterfacePlugin.instance.GSON.toJson
                        (menus.values,
                        CONFIG_TYPE
                    ), file,
                    Charsets.UTF_8
                )

            }
        }
    }

    fun saveMenu(menu: CustomMenu)
    {
        val file = File(InterfacePlugin.instance.dataFolder, "customMenus.json")


        menus[menu.id] = menu


        if (file.exists())
        {
            Files.write(
                InterfacePlugin.instance.GSON.toJson
                    (menus.values,
                    CONFIG_TYPE
                ), file,
                Charsets.UTF_8
            )

        }
    }

    override fun initiate() {
        loadMenus()
    }
}