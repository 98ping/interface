package ltd.matrixstudios.hubcore.location

import com.google.common.io.Files
import com.google.gson.reflect.TypeToken
import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.services.Service
import org.bukkit.Location
import java.io.File
import java.lang.reflect.Type

object SpawnLocationManager : Service
{
    var spawnLocation: Location? = null

    val CONFIG_TYPE: Type = object : TypeToken<Location>() {}.type

    fun loadSpawnLocation()
    {
        val file = File(InterfacePlugin.instance.dataFolder, "spawn-location.json")

        if (file.exists())
        {
            val reader = Files.newReader(file, Charsets.UTF_8)

            if (reader != null) {
               val loc = InterfacePlugin.instance.GSON.fromJson<
                        Location
                        >(
                    reader, CONFIG_TYPE
                )

                this.spawnLocation = loc
            }
        } else
        {
            file.createNewFile()

            Files.write(
                InterfacePlugin.instance.GSON.toJson
                    (null,
                    CONFIG_TYPE
                ), file,
                Charsets.UTF_8
            )
        }
    }

    fun saveSpawnLocation(location: Location)
    {
        val file = File(InterfacePlugin.instance.dataFolder, "spawn-location.json")


        spawnLocation = location


        if (file.exists())
        {
            Files.write(
                InterfacePlugin.instance.GSON.toJson
                    (location,
                    CONFIG_TYPE
                ), file,
                Charsets.UTF_8
            )

        }
    }

    override fun initiate() {
        loadSpawnLocation()
    }
}