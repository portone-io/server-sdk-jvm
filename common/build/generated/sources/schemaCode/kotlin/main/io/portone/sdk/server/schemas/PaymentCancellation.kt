package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.Long
import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * 결제 취소 내역
 */
@Serializable
@JsonClassDiscriminator("status")
public sealed interface PaymentCancellation {
  /**
   * 취소 내역 아이디
   */
  public val id: String

  /**
   * PG사 결제 취소 내역 아이디
   */
  public val pgCancellationId: String?

  /**
   * 취소 총 금액
   */
  public val totalAmount: Long

  /**
   * 취소 금액 중 면세 금액
   */
  public val taxFreeAmount: Long

  /**
   * 취소 금액 중 부가세액
   */
  public val vatAmount: Long

  /**
   * 적립형 포인트의 환불 금액
   */
  public val easyPayDiscountAmount: Long?

  /**
   * 취소 사유
   */
  public val reason: String

  /**
   * 취소 시점
   */
  public val cancelledAt: @Serializable(InstantSerializer::class) Instant?

  /**
   * 취소 요청 시점
   */
  public val requestedAt: @Serializable(InstantSerializer::class) Instant
}
