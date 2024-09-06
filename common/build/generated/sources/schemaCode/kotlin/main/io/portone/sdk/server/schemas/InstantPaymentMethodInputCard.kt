package io.portone.sdk.server.schemas

import kotlin.Boolean
import kotlin.Int
import kotlinx.serialization.Serializable

/**
 * 카드 수단 정보 입력 정보
 */
@Serializable
public data class InstantPaymentMethodInputCard(
  /**
   * 카드 인증 관련 정보
   */
  public val credential: CardCredential,
  /**
   * 카드 할부 개월 수
   */
  public val installmentMonth: Int? = null,
  /**
   * 무이자 할부 적용 여부
   */
  public val useFreeInstallmentPlan: Boolean? = null,
  /**
   * 무이자 할부 이자를 고객사가 부담할지 여부
   */
  public val useFreeInterestFromMerchant: Boolean? = null,
  /**
   * 카드 포인트 사용 여부
   */
  public val useCardPoint: Boolean? = null,
)
