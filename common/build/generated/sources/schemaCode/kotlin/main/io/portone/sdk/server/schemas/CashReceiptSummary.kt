package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 현금영수증 내역
 */
@Serializable
public data class CashReceiptSummary(
  /**
   * 발행 번호
   */
  public val issueNumber: String,
  /**
   * 현금 영수증 URL
   */
  public val url: String,
  /**
   * PG사 현금영수증 아이디
   */
  public val pgReceiptId: String,
)
