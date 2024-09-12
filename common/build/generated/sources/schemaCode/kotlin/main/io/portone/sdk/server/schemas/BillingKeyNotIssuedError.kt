package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * BillingKeyNotIssuedError
 */
@Serializable
@SerialName("BILLING_KEY_NOT_ISSUED")
internal data class BillingKeyNotIssuedError(
  override val message: String? = null,
) : DeleteBillingKeyError
