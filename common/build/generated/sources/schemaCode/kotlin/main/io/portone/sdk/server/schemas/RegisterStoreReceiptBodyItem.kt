package io.portone.sdk.server.schemas

import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 하위 상점 거래 정보
 */
@Serializable
public data class RegisterStoreReceiptBodyItem(
  /**
   * 하위 상점 사업자등록번호
   */
  public val storeBusinessRegistrationNumber: String,
  /**
   * 하위 상점명
   */
  public val storeName: String,
  /**
   * 결제 총 금액
   */
  public val totalAmount: Long,
  /**
   * 면세액
   */
  public val taxFreeAmount: Long? = null,
  /**
   * 부가세액
   */
  public val vatAmount: Long? = null,
  /**
   * 공급가액
   */
  public val supplyAmount: Long? = null,
  /**
   * 통화
   */
  public val currency: Currency,
)
