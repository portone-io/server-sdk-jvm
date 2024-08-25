package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * GetCashReceiptError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface GetCashReceiptError {
  public val message: String?
}
