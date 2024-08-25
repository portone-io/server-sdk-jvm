package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * GetPaymentsError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface GetPaymentsError {
  public val message: String?
}
