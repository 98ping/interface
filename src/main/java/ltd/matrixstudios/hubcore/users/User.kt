package ltd.matrixstudios.hubcore.users

import gg.scala.store.storage.storable.IDataStoreObject
import java.util.*

data class User(
    override val identifier: UUID,
    val username: String,
    var activeArmor: String?,
    var activeParticle: String?,
    val joinedAt: Long,
    ) : IDataStoreObject {
}