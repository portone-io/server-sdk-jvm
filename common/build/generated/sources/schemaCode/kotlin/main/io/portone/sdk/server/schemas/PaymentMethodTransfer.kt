package io.portone.sdk.server.schemas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 계좌 이체 상세 정보
 */
@Serializable
@SerialName("PaymentMethodTransfer")
public data class PaymentMethodTransfer(
  /**
   * 표준 은행 코드
   */
  public val bank: Bank? = null,
) : PaymentMethodEasyPayMethod,
    PaymentMethod
