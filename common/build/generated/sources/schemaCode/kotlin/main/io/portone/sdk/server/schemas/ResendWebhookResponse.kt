package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 웹훅 재발송 응답 정보
 */
@Serializable
public data class ResendWebhookResponse(
  /**
   * 재발송 웹훅 정보
   */
  public val webhook: PaymentWebhook,
)
