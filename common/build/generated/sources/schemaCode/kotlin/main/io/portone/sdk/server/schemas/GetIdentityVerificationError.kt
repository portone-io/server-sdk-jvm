package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * GetIdentityVerificationError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface GetIdentityVerificationError {
  public val message: String?
}
