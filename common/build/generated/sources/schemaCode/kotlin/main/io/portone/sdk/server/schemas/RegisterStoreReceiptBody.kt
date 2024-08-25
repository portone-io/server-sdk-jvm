package io.portone.sdk.server.schemas

import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

/**
 * 영수증 내 하위 상점 거래 등록 정보
 */
@Serializable
internal data class RegisterStoreReceiptBody(
  /**
   * 하위 상점 거래 목록
   */
  public val items: List<RegisterStoreReceiptBodyItem>,
  /**
   * 상점 아이디
   */
  public val storeId: String? = null,
)
