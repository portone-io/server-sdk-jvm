package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 결제 성공 상태
 */
@Serializable
@SerialName("SUCCEEDED")
public data class SucceededPaymentSchedule(
  /**
   * 결제 예약 건 아이디
   */
  override val id: String,
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
   * 빌링키
   */
  override val billingKey: String,
  /**
   * 주문명
   */
  override val orderName: String,
  /**
   * 문화비 지출 여부
   */
  override val isCulturalExpense: Boolean,
  /**
   * 에스크로 결제 여부
   */
  override val isEscrow: Boolean,
  /**
   * 고객 정보
   */
  override val customer: Customer,
  /**
   * 사용자 지정 데이터
   */
  override val customData: String,
  /**
   * 결제 총 금액
   */
  override val totalAmount: Long,
  /**
   * 면세액
   */
  override val taxFreeAmount: Long? = null,
  /**
   * 부가세
   */
  override val vatAmount: Long? = null,
  /**
   * 통화
   */
  override val currency: Currency,
  /**
   * 할부 개월 수
   */
  override val installmentMonth: Int? = null,
  /**
   * 웹훅 주소
   */
  override val noticeUrls: List<String>? = null,
  /**
   * 상품 정보
   */
  override val products: List<PaymentProduct>? = null,
  /**
   * 결제 예약 등록 시점
   */
  override val createdAt: @Serializable(InstantSerializer::class) Instant,
  /**
   * 결제 예정 시점
   */
  override val timeToPay: @Serializable(InstantSerializer::class) Instant,
  /**
   * 결제 시작 시점
   */
  public val startedAt: @Serializable(InstantSerializer::class) Instant,
  /**
   * 결제 완료 시점
   */
  public val completedAt: @Serializable(InstantSerializer::class) Instant,
) : PaymentSchedule
