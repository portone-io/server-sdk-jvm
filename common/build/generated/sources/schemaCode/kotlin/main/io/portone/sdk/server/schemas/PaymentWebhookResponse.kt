package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 웹훅 응답 정보
 */
@Serializable
public data class PaymentWebhookResponse(
  /**
   * 응답 HTTP 코드
   */
  public val code: String,
  /**
   * 응답 헤더
   */
  public val `header`: String,
  /**
   * 응답 본문
   */
  public val body: String,
  /**
   * 응답 시점
   */
  public val respondedAt: @Serializable(InstantSerializer::class) Instant,
)
