package io.portone.sdk.server.schemas

import kotlin.Long
import kotlinx.serialization.Serializable

/**
 * 결제 금액 세부 정보
 */
@Serializable
public data class PaymentAmount(
  /**
   * 총 결제금액
   */
  public val total: Long,
  /**
   * 면세액
   */
  public val taxFree: Long,
  /**
   * 부가세액
   */
  public val vat: Long? = null,
  /**
   * 공급가액
   */
  public val supply: Long? = null,
  /**
   * 할인금액
   *
   * 카드사 프로모션, 포트원 프로모션, 적립형 포인트 결제, 쿠폰 할인 등을 포함합니다.
   */
  public val discount: Long,
  /**
   * 실제 결제금액
   */
  public val paid: Long,
  /**
   * 취소금액
   */
  public val cancelled: Long,
  /**
   * 취소금액 중 면세액
   */
  public val cancelledTaxFree: Long,
)
