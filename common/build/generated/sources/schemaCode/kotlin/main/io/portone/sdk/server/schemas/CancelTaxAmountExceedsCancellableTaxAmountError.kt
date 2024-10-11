package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 취소 과세 금액이 취소 가능한 과세 금액을 초과한 경우
 */
@Serializable
@SerialName("CANCEL_TAX_AMOUNT_EXCEEDS_CANCELLABLE_TAX_AMOUNT")
internal data class CancelTaxAmountExceedsCancellableTaxAmountError(
  override val message: String? = null,
) : CancelPaymentError
