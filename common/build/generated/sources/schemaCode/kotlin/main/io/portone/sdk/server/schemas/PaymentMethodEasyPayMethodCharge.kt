package io.portone.sdk.server.schemas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 충전식 포인트 결제 정보
 */
@Serializable
@SerialName("PaymentMethodEasyPayMethodCharge")
public data class PaymentMethodEasyPayMethodCharge(
  /**
   * 표준 은행 코드
   */
  public val bank: Bank? = null,
) : PaymentMethodEasyPayMethod
