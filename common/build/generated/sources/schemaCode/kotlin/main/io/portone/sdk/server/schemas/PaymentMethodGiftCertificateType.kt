package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 상품권 종류
 */
@Serializable
public enum class PaymentMethodGiftCertificateType {
  BOOKNLIFE,
  SMART_MUNSANG,
  CULTURELAND,
  HAPPYMONEY,
  CULTUREGIFT,
}
