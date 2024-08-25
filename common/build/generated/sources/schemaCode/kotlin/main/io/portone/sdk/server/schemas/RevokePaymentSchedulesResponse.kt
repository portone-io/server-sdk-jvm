package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

/**
 * 결제 예약 건 취소 성공 응답
 */
@Serializable
public data class RevokePaymentSchedulesResponse(
  /**
   * 취소 완료된 결제 예약 건 아이디 목록
   */
  public val revokedScheduleIds: List<String>,
  /**
   * 결제 예약 건 취소 완료 시점
   */
  public val revokedAt: @Serializable(InstantSerializer::class) Instant? = null,
)
