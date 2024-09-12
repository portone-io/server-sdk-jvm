package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 결제 건 상태
 */
@Serializable
public enum class PaymentStatus {
  READY,
  PENDING,
  VIRTUAL_ACCOUNT_ISSUED,
  PAID,
  FAILED,
  PARTIAL_CANCELLED,
  CANCELLED,
}
