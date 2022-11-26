package ltd.matrixstudios.hubcore.users

import ltd.matrixstudios.hubcore.InterfacePlugin
import ltd.matrixstudios.hubcore.services.Service
import ltd.matrixstudios.syndicate.Syndicate
import ltd.matrixstudios.syndicate.orchestrators.async.AsyncRepositoryOrchestrator
import ltd.matrixstudios.syndicate.orchestrators.sync.SyncRepositoryOrchestrator
import ltd.matrixstudios.syndicate.storage.mongo.async.AsyncMongoService
import ltd.matrixstudios.syndicate.storage.mongo.sync.MongoService
import ltd.matrixstudios.syndicate.types.async.AsyncStoreType
import ltd.matrixstudios.syndicate.types.sync.SyncStoreType
import me.lucko.helper.Events
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.util.UUID
import java.util.logging.Level

object UserService : Service
{
    lateinit var controller: MongoService<User>

    var cache = mutableMapOf<UUID, User>()

    override fun initiate() {
        controller = SyncRepositoryOrchestrator.createSyncRepository(User::class.java, SyncStoreType.MONGO_SYNC) as MongoService<User>

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
        if (cache.containsKey(uuid))
        {
            return cache[uuid]!!
        }

       val there = controller.findById(uuid)

        if (there != null)
        {
            cache[there.id] = there

            return there
        }

        return User(username, null, null, System.currentTimeMillis(), uuid).also { cache[it.id] = it }
    }


}