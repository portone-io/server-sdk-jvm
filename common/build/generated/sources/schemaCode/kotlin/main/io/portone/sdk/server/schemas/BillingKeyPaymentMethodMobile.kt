package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 모바일 정보
 */
@Serializable
@SerialName("BillingKeyPaymentMethodMobile")
public data class BillingKeyPaymentMethodMobile(
  /**
   * 전화번호
   */
  public val phoneNumber: String? = null,
) : BillingKeyPaymentMethod
