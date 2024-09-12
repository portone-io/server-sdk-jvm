package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * CancelPaymentError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface CancelPaymentError {
  public val message: String?
}
