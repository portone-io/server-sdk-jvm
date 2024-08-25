package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * IssueCashReceiptError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface IssueCashReceiptError {
  public val message: String?
}
