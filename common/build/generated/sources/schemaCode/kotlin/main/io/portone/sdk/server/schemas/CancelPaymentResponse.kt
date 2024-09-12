package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 결제 취소 성공 응답
 */
@Serializable
public data class CancelPaymentResponse(
  /**
   * 결체 취소 내역
   */
  public val cancellation: PaymentCancellation,
)
