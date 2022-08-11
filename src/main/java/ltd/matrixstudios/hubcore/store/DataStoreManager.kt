package ltd.matrixstudios.hubcore.store

import gg.scala.store.ScalaDataStoreShared
import gg.scala.store.connection.mongo.AbstractDataStoreMongoConnection
import gg.scala.store.connection.mongo.impl.UriDataStoreMongoConnection
import gg.scala.store.connection.mongo.impl.details.DataStoreMongoConnectionDetails
import gg.scala.store.connection.redis.AbstractDataStoreRedisConnection
import gg.scala.store.connection.redis.impl.NoAuthDataStoreRedisConnection
import gg.scala.store.connection.redis.impl.details.DataStoreRedisConnectionDetails
import ltd.matrixstudios.hubcore.InterfacePlugin

object DataStoreManager : ScalaDataStoreShared() {
    override fun debug(from: String, message: String) {
        return
    }

    override fun getNewRedisConnection(): AbstractDataStoreRedisConnection {

        return NoAuthDataStoreRedisConnection(
            DataStoreRedisConnectionDetails("localhost")
        )
    }

    override fun getNewMongoConnection(): AbstractDataStoreMongoConnection
    {
        return UriDataStoreMongoConnection(
            DataStoreMongoConnectionDetails(InterfacePlugin.instance.config.getString("mongo"), "interface")
        )
    }
}