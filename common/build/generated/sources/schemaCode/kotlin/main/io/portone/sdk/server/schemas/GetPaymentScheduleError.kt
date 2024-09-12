package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * GetPaymentScheduleError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface GetPaymentScheduleError {
  public val message: String?
}
