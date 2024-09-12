package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 발급 완료
 */
@Serializable
@SerialName("ISSUED")
public data class IssuedCashReceipt(
  /**
   * 고객사 아이디
   */
  override val merchantId: String,
  /**
   * 상점 아이디
   */
  override val storeId: String,
  /**
   * 결제 건 아이디
   */
  override val paymentId: String,
  /**
   * 현금영수증 발급에 사용된 채널
   */
  override val channel: SelectedChannel,
  /**
   * 결제 금액
   */
  public val amount: Long,
  /**
   * 면세액
   */
  public val taxFreeAmount: Long? = null,
  /**
   * 부가세액
   */
  public val vatAmount: Long? = null,
  /**
   * 통화
   */
  public val currency: Currency,
  /**
   * 주문명
   */
  override val orderName: String,
  /**
   * 수동 발급 여부
   */
  override val isManual: Boolean,
  /**
   * 현금영수증 유형
   */
  public val type: CashReceiptType? = null,
  /**
   * PG사 현금영수증 아이디
   */
  public val pgReceiptId: String? = null,
  /**
   * 승인 번호
   */
  public val issueNumber: String,
  /**
   * 현금영수증 URL
   */
  public val url: String? = null,
  /**
   * 발급 시점
   */
  public val issuedAt: @Serializable(InstantSerializer::class) Instant,
) : CashReceipt
