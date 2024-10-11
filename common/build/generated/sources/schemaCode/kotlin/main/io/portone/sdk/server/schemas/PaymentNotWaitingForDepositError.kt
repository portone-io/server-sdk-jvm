package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 결제 건이 입금 대기 상태가 아닌 경우
 */
@Serializable
@SerialName("PAYMENT_NOT_WAITING_FOR_DEPOSIT")
internal data class PaymentNotWaitingForDepositError(
  override val message: String? = null,
) : CloseVirtualAccountError
