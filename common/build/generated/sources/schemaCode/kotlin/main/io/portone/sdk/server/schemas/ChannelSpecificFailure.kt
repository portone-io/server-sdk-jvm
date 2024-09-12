package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * ChannelSpecificFailure
 */
@Serializable
@JsonClassDiscriminator("type")
public sealed interface ChannelSpecificFailure {
  public val channel: SelectedChannel

  public val message: String?
}
