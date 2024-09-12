package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * ApplyEscrowLogisticsError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface ApplyEscrowLogisticsError {
  public val message: String?
}
