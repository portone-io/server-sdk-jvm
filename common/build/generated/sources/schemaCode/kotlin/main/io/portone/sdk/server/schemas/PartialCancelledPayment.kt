package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 결제 부분 취소 상태 건
 */
@Serializable
@SerialName("PARTIAL_CANCELLED")
public data class PartialCancelledPayment(
  /**
   * 결제 건 아이디
   */
  override val id: String,
  /**
   * 결제 건 포트원 채번 아이디
   *
   * V1 결제 건의 경우 imp_uid에 해당합니다.
   */
  public val transactionId: String,
  /**
   * 고객사 아이디
   */
  override val merchantId: String,
  /**
   * 상점 아이디
   */
  override val storeId: String,
  /**
   * 결제수단 정보
   */
  override val method: PaymentMethod? = null,
  /**
   * 결제 채널
   */
  override val channel: SelectedChannel,
  /**
   * 결제 채널 그룹 정보
   */
  override val channelGroup: ChannelGroupSummary? = null,
  /**
   * 포트원 버전
   */
  override val version: PortOneVersion,
  /**
   * 결제 예약 건 아이디
   *
   * 결제 예약을 이용한 경우에만 존재
   */
  override val scheduleId: String? = null,
  /**
   * 결제 시 사용된 빌링키
   *
   * 빌링키 결제인 경우에만 존재
   */
  override val billingKey: String? = null,
  /**
   * 웹훅 발송 내역
   */
  override val webhooks: List<PaymentWebhook>? = null,
  /**
   * 결제 요청 시점
   */
  override val requestedAt: @Serializable(InstantSerializer::class) Instant,
  /**
   * 업데이트 시점
   */
  override val updatedAt: @Serializable(InstantSerializer::class) Instant,
  /**
   * 상태 업데이트 시점
   */
  override val statusChangedAt: @Serializable(InstantSerializer::class) Instant,
  /**
   * 주문명
   */
  override val orderName: String,
  /**
   * 결제 금액 관련 세부 정보
   */
  override val amount: PaymentAmount,
  /**
   * 통화
   */
  override val currency: Currency,
  /**
   * 구매자 정보
   */
  override val customer: Customer,
  /**
   * 프로모션 아이디
   */
  override val promotionId: String? = null,
  /**
   * 문화비 지출 여부
   */
  override val isCulturalExpense: Boolean? = null,
  /**
   * 에스크로 결제 정보
   *
   * 에스크로 결제인 경우 존재합니다.
   */
  override val escrow: PaymentEscrow? = null,
  /**
   * 상품 정보
   */
  override val products: List<PaymentProduct>? = null,
  /**
   * 상품 갯수
   */
  override val productCount: Int? = null,
  /**
   * 사용자 지정 데이터
   */
  override val customData: String? = null,
  /**
   * 국가 코드
   */
  override val country: Country? = null,
  /**
   * 결제 완료 시점
   */
  public val paidAt: @Serializable(InstantSerializer::class) Instant? = null,
  /**
   * PG사 거래 아이디
   */
  public val pgTxId: String? = null,
  /**
   * 현금영수증
   */
  public val cashReceipt: PaymentCashReceipt? = null,
  /**
   * 거래 영수증 URL
   */
  public val receiptUrl: String? = null,
  /**
   * 결제 취소 내역
   */
  public val cancellations: List<PaymentCancellation>,
  /**
   * 결제 취소 시점
   */
  public val cancelledAt: @Serializable(InstantSerializer::class) Instant,
) : Payment
