package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * ResendWebhookError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface ResendWebhookError {
  public val message: String?
}
