package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * PayWithBillingKeyError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface PayWithBillingKeyError {
  public val message: String?
}
