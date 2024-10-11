package io.portone.sdk.server.schemas

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 결제수단 카드 정보
 */
@Serializable
@SerialName("PaymentMethodCard")
public data class PaymentMethodCard(
  /**
   * 카드 상세 정보
   */
  public val card: Card? = null,
  /**
   * 승인 번호
   */
  public val approvalNumber: String? = null,
  /**
   * 할부 정보
   */
  public val installment: PaymentInstallment? = null,
  /**
   * 카드 포인트 사용여부
   */
  public val pointUsed: Boolean? = null,
) : PaymentMethod,
    PaymentMethodEasyPayMethod
