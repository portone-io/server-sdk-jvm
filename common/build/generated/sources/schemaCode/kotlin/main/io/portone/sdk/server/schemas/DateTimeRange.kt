package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlinx.serialization.Serializable

/**
 * 시간 범위
 */
@Serializable
public data class DateTimeRange(
  public val from: @Serializable(InstantSerializer::class) Instant,
  public val until: @Serializable(InstantSerializer::class) Instant,
)
