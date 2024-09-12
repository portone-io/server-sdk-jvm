package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * GetPaymentError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface GetPaymentError {
  public val message: String?
}
