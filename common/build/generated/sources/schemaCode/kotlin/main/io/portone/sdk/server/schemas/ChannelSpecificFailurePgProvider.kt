package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * PG사에서 오류를 전달한 경우
 */
@Serializable
@SerialName("PG_PROVIDER")
public data class ChannelSpecificFailurePgProvider(
  override val channel: SelectedChannel,
  override val message: String? = null,
  public val pgCode: String,
  public val pgMessage: String,
) : ChannelSpecificFailure
