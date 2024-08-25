package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * PreRegisterPaymentError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface PreRegisterPaymentError {
  public val message: String?
}
