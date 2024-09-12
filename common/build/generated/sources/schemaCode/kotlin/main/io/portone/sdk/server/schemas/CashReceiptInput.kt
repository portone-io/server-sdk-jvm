package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 현금영수증 입력 정보
 */
@Serializable
public data class CashReceiptInput(
  /**
   * 현금영수증 유형
   */
  public val type: CashReceiptInputType,
  /**
   * 사용자 식별 번호
   *
   * 미발행 유형 선택 시 입력하지 않습니다.
   */
  public val customerIdentityNumber: String? = null,
)
