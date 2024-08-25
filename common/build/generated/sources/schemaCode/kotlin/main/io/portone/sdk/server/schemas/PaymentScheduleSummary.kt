package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 결제 예약 건
 */
@Serializable
public data class PaymentScheduleSummary(
  /**
   * 결제 예약 건 아이디
   */
  public val id: String,
)
