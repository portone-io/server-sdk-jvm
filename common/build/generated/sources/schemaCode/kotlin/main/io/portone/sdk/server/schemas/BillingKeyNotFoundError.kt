package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 빌링키가 존재하지 않는 경우
 */
@Serializable
@SerialName("BILLING_KEY_NOT_FOUND")
internal data class BillingKeyNotFoundError(
  override val message: String? = null,
) : GetBillingKeyInfoError,
    DeleteBillingKeyError,
    RevokePaymentSchedulesError,
    CreatePaymentScheduleError,
    PayWithBillingKeyError
