package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 웹훅 요청 정보
 */
@Serializable
public data class PaymentWebhookRequest(
  /**
   * 요청 헤더
   */
  public val `header`: String? = null,
  /**
   * 요청 본문
   */
  public val body: String,
  /**
   * 요청 시점
   */
  public val requestedAt: @Serializable(InstantSerializer::class) Instant? = null,
)
