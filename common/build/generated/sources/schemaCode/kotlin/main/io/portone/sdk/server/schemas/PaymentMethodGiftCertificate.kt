package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 상품권 상세 정보
 */
@Serializable
@SerialName("PaymentMethodGiftCertificate")
public data class PaymentMethodGiftCertificate(
  /**
   * 상품권 종류
   */
  public val giftCertificateType: PaymentMethodGiftCertificateType? = null,
  /**
   * 상품권 승인 번호
   */
  public val approvalNumber: String,
) : PaymentMethod
