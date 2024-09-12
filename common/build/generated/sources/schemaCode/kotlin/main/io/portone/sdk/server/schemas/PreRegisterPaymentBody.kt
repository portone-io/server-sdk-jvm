package io.portone.sdk.server.schemas

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 결제 정보 사전 등록 입력 정보
 */
@Serializable
internal data class PreRegisterPaymentBody(
  /**
   * 상점 아이디
   *
   * 접근 권한이 있는 상점 아이디만 입력 가능하며, 미입력시 토큰에 담긴 상점 아이디를 사용합니다.
   */
  public val storeId: String? = null,
  /**
   * 결제 총 금액
   */
  public val totalAmount: Long? = null,
  /**
   * 결제 면세 금액
   */
  public val taxFreeAmount: Long? = null,
  /**
   * 통화 단위
   */
  public val currency: Currency? = null,
)
