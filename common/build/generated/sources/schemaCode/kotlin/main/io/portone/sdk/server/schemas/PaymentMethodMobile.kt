package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 모바일 상세 정보
 */
@Serializable
@SerialName("PaymentMethodMobile")
public data class PaymentMethodMobile(
  /**
   * 전화번호
   */
  public val phoneNumber: String? = null,
) : PaymentMethod
