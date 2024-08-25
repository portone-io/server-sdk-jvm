package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 인증 정보가 올바르지 않은 경우
 */
@Serializable
@SerialName("UNAUTHORIZED")
internal data class UnauthorizedError(
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
    GetKakaopayPaymentOrderError,
    RegisterStoreReceiptError
