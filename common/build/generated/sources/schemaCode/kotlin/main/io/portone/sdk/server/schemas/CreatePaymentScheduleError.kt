package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * CreatePaymentScheduleError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface CreatePaymentScheduleError {
  public val message: String?
}
