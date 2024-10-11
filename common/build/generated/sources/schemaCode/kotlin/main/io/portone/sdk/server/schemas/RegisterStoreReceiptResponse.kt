package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 영수증 내 하위 상점 거래 등록 응답
 */
@Serializable
public data class RegisterStoreReceiptResponse(
  /**
   * 결제 영수증 URL
   */
  public val receiptUrl: String? = null,
)
