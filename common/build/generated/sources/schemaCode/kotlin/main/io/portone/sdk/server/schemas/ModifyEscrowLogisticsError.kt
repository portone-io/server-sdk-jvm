package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * ModifyEscrowLogisticsError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface ModifyEscrowLogisticsError {
  public val message: String?
}
