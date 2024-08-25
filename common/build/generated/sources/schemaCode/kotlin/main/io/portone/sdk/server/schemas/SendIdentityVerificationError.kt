package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * SendIdentityVerificationError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface SendIdentityVerificationError {
  public val message: String?
}
