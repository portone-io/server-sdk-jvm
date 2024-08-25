package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * DeleteBillingKeyError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface DeleteBillingKeyError {
  public val message: String?
}
