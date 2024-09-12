package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * PayInstantlyError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface PayInstantlyError {
  public val message: String?
}
