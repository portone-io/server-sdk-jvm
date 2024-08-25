package io.portone.sdk.server.schemas

import kotlin.Boolean
import kotlin.Int
import kotlinx.serialization.Serializable

/**
 * 할부 정보
 */
@Serializable
public data class PaymentInstallment(
  /**
   * 할부 개월 수
   */
  public val month: Int,
  /**
   * 무이자할부 여부
   */
  public val isInterestFree: Boolean,
)
