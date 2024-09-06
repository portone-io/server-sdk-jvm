package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 결제수단이 프로모션에 지정된 것과 일치하지 않는 경우
 */
@Serializable
@SerialName("PROMOTION_PAY_METHOD_DOES_NOT_MATCH")
internal data class PromotionPayMethodDoesNotMatchError(
  override val message: String? = null,
) : PayWithBillingKeyError,
    PayInstantlyError
