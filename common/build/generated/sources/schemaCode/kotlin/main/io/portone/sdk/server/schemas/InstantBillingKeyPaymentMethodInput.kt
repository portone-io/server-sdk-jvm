package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 빌링키 발급 시 결제 수단 입력 양식
 */
@Serializable
public data class InstantBillingKeyPaymentMethodInput(
  public val card: InstantBillingKeyPaymentMethodInputCard? = null,
)
