package ltd.matrixstudios.hubcore.users

import ltd.matrixstudios.syndicate.objects.IStoreObject
import java.util.*

data class User(
    val username: String,
    var activeArmor: String?,
    var activeParticle: String?,
    val joinedAt: Long, override val id: UUID,
    ) : IStoreObject {
}