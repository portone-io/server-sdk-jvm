package io.portone.sdk.server.schemas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 간편 결제 상세 정보
 */
@Serializable
@SerialName("PaymentMethodEasyPay")
public data class PaymentMethodEasyPay(
  /**
   * 간편 결제 PG사
   */
  public val provider: EasyPayProvider? = null,
  /**
   * 간편 결제 수단
   */
  public val easyPayMethod: PaymentMethodEasyPayMethod? = null,
) : PaymentMethod
