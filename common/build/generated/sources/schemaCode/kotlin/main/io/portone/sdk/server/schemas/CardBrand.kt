package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 카드 브랜드
 */
@Serializable
public enum class CardBrand {
  LOCAL,
  MASTER,
  UNIONPAY,
  VISA,
  JCB,
  AMEX,
  DINERS,
}
