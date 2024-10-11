package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 배송정보
 */
@Serializable
public data class PaymentLogistics(
  /**
   * 물류회사
   */
  public val company: PaymentLogisticsCompany,
  /**
   * 송장번호
   */
  public val invoiceNumber: String,
  /**
   * 발송시점
   */
  public val sentAt: @Serializable(InstantSerializer::class) Instant,
  /**
   * 수령시점
   */
  public val receivedAt: @Serializable(InstantSerializer::class) Instant? = null,
  /**
   * 주소
   */
  public val address: SeparatedAddressInput? = null,
)
