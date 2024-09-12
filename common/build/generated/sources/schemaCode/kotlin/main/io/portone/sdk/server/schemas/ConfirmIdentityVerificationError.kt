package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * ConfirmIdentityVerificationError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface ConfirmIdentityVerificationError {
  public val message: String?
}
