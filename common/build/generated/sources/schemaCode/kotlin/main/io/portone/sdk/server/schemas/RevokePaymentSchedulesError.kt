package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * RevokePaymentSchedulesError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface RevokePaymentSchedulesError {
  public val message: String?
}
