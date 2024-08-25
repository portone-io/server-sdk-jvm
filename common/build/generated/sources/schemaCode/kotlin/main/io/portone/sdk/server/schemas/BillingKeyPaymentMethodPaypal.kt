package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 페이팔 정보
 */
@Serializable
public data object BillingKeyPaymentMethodPaypal : BillingKeyPaymentMethod
