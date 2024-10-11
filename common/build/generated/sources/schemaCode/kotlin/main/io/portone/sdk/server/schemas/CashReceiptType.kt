package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 발급 유형
 */
@Serializable
public enum class CashReceiptType {
  /**
   * 소득공제용
   */
  PERSONAL,
  /**
   * 지출증빙용
   */
  CORPORATE,
}
