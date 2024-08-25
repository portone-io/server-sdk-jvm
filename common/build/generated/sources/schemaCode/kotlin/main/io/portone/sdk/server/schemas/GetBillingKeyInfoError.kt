package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * GetBillingKeyInfoError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface GetBillingKeyInfoError {
  public val message: String?
}
