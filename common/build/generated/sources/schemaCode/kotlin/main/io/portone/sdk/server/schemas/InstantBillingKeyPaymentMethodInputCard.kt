package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 카드 수단 정보 입력 양식
 */
@Serializable
public data class InstantBillingKeyPaymentMethodInputCard(
  public val credential: CardCredential,
)
