package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 에스크로 수취인 정보
 */
@Serializable
public data class PaymentEscrowReceiverInput(
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
   * 주소
   */
  public val address: SeparatedAddressInput? = null,
)
