package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * RegisterStoreReceiptError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface RegisterStoreReceiptError {
  public val message: String?
}
