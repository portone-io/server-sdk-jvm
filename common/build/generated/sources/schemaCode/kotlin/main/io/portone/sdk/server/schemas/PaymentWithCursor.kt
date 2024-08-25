package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 결제 건 및 커서 정보
 */
@Serializable
public data class PaymentWithCursor(
  /**
   * 결제 건 정보
   */
  public val payment: Payment,
  /**
   * 해당 결제 건의 커서 정보
   */
  public val cursor: String,
)
