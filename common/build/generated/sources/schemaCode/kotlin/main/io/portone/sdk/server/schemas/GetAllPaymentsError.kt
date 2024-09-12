package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * GetAllPaymentsError
 */
@Serializable
@JsonClassDiscriminator("type")
internal sealed interface GetAllPaymentsError {
  public val message: String?
}
