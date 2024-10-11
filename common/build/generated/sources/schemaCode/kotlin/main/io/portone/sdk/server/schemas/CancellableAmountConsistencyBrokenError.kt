package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 취소 가능 잔액 검증에 실패한 경우
 */
@Serializable
@SerialName("CANCELLABLE_AMOUNT_CONSISTENCY_BROKEN")
internal data class CancellableAmountConsistencyBrokenError(
  override val message: String? = null,
) : CancelPaymentError
