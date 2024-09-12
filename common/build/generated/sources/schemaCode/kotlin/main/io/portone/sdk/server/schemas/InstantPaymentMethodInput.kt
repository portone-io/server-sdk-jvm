package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 수기 결제 수단 입력 정보
 *
 * 하나의 필드만 입력합니다.
 */
@Serializable
public data class InstantPaymentMethodInput(
  /**
   * 카드
   */
  public val card: InstantPaymentMethodInputCard? = null,
  /**
   * 가상계좌
   */
  public val virtualAccount: InstantPaymentMethodInputVirtualAccount? = null,
)
