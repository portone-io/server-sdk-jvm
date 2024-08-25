package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 성공 웹훅 내역
 */
@Serializable
public data class PaymentWebhook(
  /**
   * 웹훅 발송 시 결제 건 상태
   *
   * V1 결제 건인 경우, 값이 존재하지 않습니다.
   */
  public val paymentStatus: PaymentWebhookPaymentStatus? = null,
  /**
   * 웹훅 아이디
   */
  public val id: String,
  /**
   * 웹훅 상태
   */
  public val status: PaymentWebhookStatus? = null,
  /**
   * 웹훅이 발송된 url
   *
   * V1 결제 건인 경우, 값이 존재하지 않습니다.
   */
  public val url: String,
  /**
   * 비동기 웹훅 여부
   *
   * V1 결제 건인 경우, 값이 존재하지 않습니다.
   */
  public val isAsync: Boolean? = null,
  /**
   * 현재 발송 횟수
   */
  public val currentExecutionCount: Int? = null,
  /**
   * 최대 발송 횟수
   */
  public val maxExecutionCount: Int? = null,
  /**
   * 웹훅 실행 맥락
   */
  public val trigger: PaymentWebhookTrigger? = null,
  /**
   * 웹훅 요청 정보
   */
  public val request: PaymentWebhookRequest? = null,
  /**
   * 웹훅 응답 정보
   */
  public val response: PaymentWebhookResponse? = null,
  /**
   * 웹훅 처리 시작 시점
   */
  public val triggeredAt: @Serializable(InstantSerializer::class) Instant? = null,
)
