package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 결제가 완료되지 않은 경우
 */
@Serializable
@SerialName("PAYMENT_NOT_PAID")
internal data class PaymentNotPaidError(
  override val message: String? = null,
) : CancelPaymentError,
    ApplyEscrowLogisticsError,
    ModifyEscrowLogisticsError,
    ConfirmEscrowError,
    RegisterStoreReceiptError
