package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * ConfirmEscrowError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface ConfirmEscrowError {
  public val message: String?
}
