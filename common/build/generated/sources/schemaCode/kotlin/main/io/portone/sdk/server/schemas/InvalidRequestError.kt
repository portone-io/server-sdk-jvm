package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 요청된 입력 정보가 유효하지 않은 경우
 *
 * 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
 */
@Serializable
@SerialName("INVALID_REQUEST")
internal data class InvalidRequestError(
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
