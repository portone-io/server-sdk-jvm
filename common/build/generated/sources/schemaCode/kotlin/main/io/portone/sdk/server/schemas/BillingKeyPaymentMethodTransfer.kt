package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 계좌이체 정보
 */
@Serializable
@SerialName("BillingKeyPaymentMethodTransfer")
public data class BillingKeyPaymentMethodTransfer(
  /**
   * 표준 은행 코드
   */
  public val bank: Bank? = null,
  /**
   * 계좌번호
   */
  public val accountNumber: String? = null,
) : BillingKeyPaymentMethodEasyPayMethod,
    BillingKeyPaymentMethod
