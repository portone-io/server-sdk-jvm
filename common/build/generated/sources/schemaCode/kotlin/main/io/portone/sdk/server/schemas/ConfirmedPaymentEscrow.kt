package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 구매 확정
 */
@Serializable
@SerialName("CONFIRMED")
public data class ConfirmedPaymentEscrow(
  /**
   * 택배사
   */
  public val company: String,
  /**
   * 송장번호
   */
  public val invoiceNumber: String,
  /**
   * 발송 일시
   */
  public val sentAt: @Serializable(InstantSerializer::class) Instant? = null,
  /**
   * 배송등록 처리 일자
   */
  public val appliedAt: @Serializable(InstantSerializer::class) Instant? = null,
  /**
   * 자동 구매 확정 처리 여부
   */
  public val isAutomaticallyConfirmed: Boolean,
) : PaymentEscrow
