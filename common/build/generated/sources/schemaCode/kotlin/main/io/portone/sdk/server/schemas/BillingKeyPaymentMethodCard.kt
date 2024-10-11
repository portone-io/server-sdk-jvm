package io.portone.sdk.server.schemas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 카드 정보
 */
@Serializable
@SerialName("BillingKeyPaymentMethodCard")
public data class BillingKeyPaymentMethodCard(
  /**
   * 카드 상세 정보
   */
  public val card: Card? = null,
) : BillingKeyPaymentMethod,
    BillingKeyPaymentMethodEasyPayMethod
