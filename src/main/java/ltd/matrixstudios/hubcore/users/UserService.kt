package ltd.matrixstudios.hubcore.users

import gg.scala.store.controller.DataStoreObjectController
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.type.DataStoreStorageType
import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.services.Service
import me.lucko.helper.Events
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.util.UUID
import java.util.logging.Level

object UserService : Service
{
    lateinit var controller: DataStoreObjectController<User>

    override fun initiate() {
        controller =  DataStoreObjectControllerCache.create()

        controller.preLoadResources()

        loadListeners()
    }

    fun loadListeners()
    {
        Events.subscribe(
            AsyncPlayerPreLoginEvent::class.java
        ).handler {
            val startingTime = System.currentTimeMillis()
            val uuid = it.uniqueId

            loadUser(uuid, it.name)

            InterfacePlugin.instance.logger.log(Level.INFO, "[users] User ${it.name} had their profile loaded in ${System.currentTimeMillis().minus(startingTime)} milliseconds")
        }
    }

    fun retrieveAllCached() : MutableMap<UUID, User>
    {
        return controller.localCache
    }

    fun loadUser(uuid: UUID, username: String) : User
    {
        val cache = retrieveAllCached()

        return if (cache.containsKey(uuid)) {
            cache[uuid]!!
        } else {
            val generatingProfile = User(uuid, username, null, null, System.currentTimeMillis())

            controller.save(generatingProfile, DataStoreStorageType.MONGO)

            controller.localCache[uuid] = generatingProfile

            generatingProfile
        }
    }


}