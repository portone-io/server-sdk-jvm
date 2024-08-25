package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 결제 건이 존재하지 않는 경우
 */
@Serializable
@SerialName("PAYMENT_NOT_FOUND")
internal data class PaymentNotFoundError(
  override val message: String? = null,
) : GetPaymentError,
    CancelPaymentError,
    CloseVirtualAccountError,
    ApplyEscrowLogisticsError,
    ModifyEscrowLogisticsError,
    ConfirmEscrowError,
    ResendWebhookError,
    RegisterStoreReceiptError
