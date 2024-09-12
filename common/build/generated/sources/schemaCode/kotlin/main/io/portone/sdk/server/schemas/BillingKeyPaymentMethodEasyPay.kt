package io.portone.sdk.server.schemas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 간편 결제 정보
 */
@Serializable
@SerialName("BillingKeyPaymentMethodEasyPay")
public data class BillingKeyPaymentMethodEasyPay(
  /**
   * 간편 결제 PG사
   */
  public val provider: EasyPayProvider? = null,
  /**
   * 간편 결제 수단
   */
  public val method: BillingKeyPaymentMethodEasyPayMethod? = null,
) : BillingKeyPaymentMethod
