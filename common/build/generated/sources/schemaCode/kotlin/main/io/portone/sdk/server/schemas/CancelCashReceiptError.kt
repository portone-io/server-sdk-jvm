package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * CancelCashReceiptError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface CancelCashReceiptError {
  public val message: String?
}
