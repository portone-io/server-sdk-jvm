package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 결제가 이미 완료된 경우
 */
@Serializable
@SerialName("ALREADY_PAID")
internal data class AlreadyPaidError(
  override val message: String? = null,
) : PreRegisterPaymentError,
    PayWithBillingKeyError,
    PayInstantlyError
