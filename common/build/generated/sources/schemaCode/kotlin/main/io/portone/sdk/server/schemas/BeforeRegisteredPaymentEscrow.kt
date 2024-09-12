package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 배송 정보 등록 전
 */
@Serializable
public data object BeforeRegisteredPaymentEscrow : PaymentEscrow
