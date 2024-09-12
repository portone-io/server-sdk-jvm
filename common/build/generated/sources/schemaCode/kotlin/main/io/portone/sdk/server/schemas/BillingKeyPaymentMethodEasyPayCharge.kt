package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 충전식 포인트 결제 정보
 */
@Serializable
public data object BillingKeyPaymentMethodEasyPayCharge : BillingKeyPaymentMethodEasyPayMethod
