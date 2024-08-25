package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * GetPaymentSchedulesError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface GetPaymentSchedulesError {
  public val message: String?
}
