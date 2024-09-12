package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * GetBillingKeyInfosError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface GetBillingKeyInfosError {
  public val message: String?
}
