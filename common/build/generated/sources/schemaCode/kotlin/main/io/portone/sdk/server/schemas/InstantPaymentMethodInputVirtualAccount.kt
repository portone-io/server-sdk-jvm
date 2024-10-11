package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 가상계좌 수단 정보 입력 정보
 */
@Serializable
public data class InstantPaymentMethodInputVirtualAccount(
  /**
   * 은행
   */
  public val bank: Bank,
  /**
   * 입금 만료 기한
   */
  public val expiry: InstantPaymentMethodInputVirtualAccountExpiry,
  /**
   * 가상계좌 유형
   */
  public val option: InstantPaymentMethodInputVirtualAccountOption,
  /**
   * 현금영수증 정보
   */
  public val cashReceipt: InstantPaymentMethodInputVirtualAccountCashReceiptInfo,
  /**
   * 예금주명
   */
  public val remitteeName: String? = null,
)
