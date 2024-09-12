package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * GetKakaopayPaymentOrderError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface GetKakaopayPaymentOrderError {
  public val message: String?
}
