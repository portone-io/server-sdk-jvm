package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 빌링키 상태
 */
@Serializable
public enum class BillingKeyStatus {
  ISSUED,
  DELETED,
}
