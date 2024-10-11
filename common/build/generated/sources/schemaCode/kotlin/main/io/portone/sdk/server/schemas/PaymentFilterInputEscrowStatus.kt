package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 에스크로 상태
 */
@Serializable
public enum class PaymentFilterInputEscrowStatus {
  REGISTERED,
  DELIVERED,
  CONFIRMED,
  REJECTED,
  CANCELLED,
  REJECT_CONFIRMED,
}
