package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 빌링키 결제 성공 응답
 */
@Serializable
public data class PayWithBillingKeyResponse(
  /**
   * 결제 건 요약 정보
   */
  public val payment: BillingKeyPaymentSummary,
)
