package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 수기 결제 성공 응답
 */
@Serializable
public data class PayInstantlyResponse(
  /**
   * 결제 건 요약 정보
   */
  public val payment: InstantPaymentSummary,
)
