package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * CloseVirtualAccountError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface CloseVirtualAccountError {
  public val message: String?
}
