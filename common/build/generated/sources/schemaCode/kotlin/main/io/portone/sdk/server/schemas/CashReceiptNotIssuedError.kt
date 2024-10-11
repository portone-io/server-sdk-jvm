package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 현금영수증이 발급되지 않은 경우
 */
@Serializable
@SerialName("CASH_RECEIPT_NOT_ISSUED")
internal data class CashReceiptNotIssuedError(
  override val message: String? = null,
) : CancelCashReceiptError
