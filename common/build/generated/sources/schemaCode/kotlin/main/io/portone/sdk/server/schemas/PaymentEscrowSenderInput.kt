package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 에스크로 발송자 정보
 */
@Serializable
public data class PaymentEscrowSenderInput(
  /**
   * 이름
   */
  public val name: String? = null,
  /**
   * 전화번호
   */
  public val phoneNumber: String? = null,
  /**
   * 우편번호
   */
  public val zipcode: String? = null,
  /**
   * 수취인과의 관계
   */
  public val relationship: String? = null,
  /**
   * 주소
   */
  public val address: SeparatedAddressInput? = null,
)
