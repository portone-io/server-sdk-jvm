package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 고객 정보 입력 형식
 */
@Serializable
public data class CancelPaymentBodyRefundAccount(
  /**
   * 은행
   */
  public val bank: Bank,
  /**
   * 계좌번호
   */
  public val number: String,
  /**
   * 예금주
   */
  public val holderName: String,
  /**
   * 예금주 연락처 - 스마트로 가상계좌 결제인 경우에 필요합니다.
   */
  public val holderPhoneNumber: String? = null,
)
