package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 결제 예약 건 정렬 기준
 */
@Serializable
public enum class PaymentScheduleSortBy {
  /**
   * 결제 예약 생성 시각
   */
  CREATED_AT,
  /**
   * 결제 예정 시각
   */
  TIME_TO_PAY,
  /**
   * 예약 결제 시도(성공 / 실패) 시각
   */
  COMPLETED_AT,
}
