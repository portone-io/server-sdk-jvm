package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * PaymentMethodType
 */
@Serializable
public enum class PaymentMethodType {
  CARD,
  TRANSFER,
  VIRTUAL_ACCOUNT,
  GIFT_CERTIFICATE,
  MOBILE,
  EASY_PAY,
}
