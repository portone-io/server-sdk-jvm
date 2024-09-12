package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * ResendIdentityVerificationError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface ResendIdentityVerificationError {
  public val message: String?
}
