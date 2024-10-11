package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlinx.serialization.Serializable

/**
 * 에스크로 구매 확정 성공 응답
 */
@Serializable
public data class ConfirmEscrowResponse(
  /**
   * 에스크로 구매 확정 시점
   */
  public val completedAt: @Serializable(InstantSerializer::class) Instant,
)
