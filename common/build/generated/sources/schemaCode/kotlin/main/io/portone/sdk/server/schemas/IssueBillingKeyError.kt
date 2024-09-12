package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * IssueBillingKeyError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface IssueBillingKeyError {
  public val message: String?
}
