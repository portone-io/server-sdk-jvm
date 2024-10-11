package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 요청이 거절된 경우
 */
@Serializable
@SerialName("FORBIDDEN")
internal data class ForbiddenError(
  override val message: String? = null,
) : GetIdentityVerificationError,
    SendIdentityVerificationError,
    ConfirmIdentityVerificationError,
    ResendIdentityVerificationError,
    PreRegisterPaymentError,
    GetBillingKeyInfoError,
    DeleteBillingKeyError,
    GetBillingKeyInfosError,
    IssueBillingKeyError,
    GetCashReceiptError,
    GetPaymentError,
    GetPaymentsError,
    GetAllPaymentsError,
    GetPaymentScheduleError,
    GetPaymentSchedulesError,
    RevokePaymentSchedulesError,
    CreatePaymentScheduleError,
    CancelPaymentError,
    PayWithBillingKeyError,
    PayInstantlyError,
    IssueCashReceiptError,
    CancelCashReceiptError,
    CloseVirtualAccountError,
    ApplyEscrowLogisticsError,
    ModifyEscrowLogisticsError,
    ConfirmEscrowError,
    ResendWebhookError,
    RegisterStoreReceiptError
