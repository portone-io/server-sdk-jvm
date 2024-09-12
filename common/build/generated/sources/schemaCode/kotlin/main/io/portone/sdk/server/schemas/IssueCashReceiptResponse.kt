package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 현금 영수증 발급 성공 응답
 */
@Serializable
public data class IssueCashReceiptResponse(
  public val cashReceipt: CashReceiptSummary,
)
