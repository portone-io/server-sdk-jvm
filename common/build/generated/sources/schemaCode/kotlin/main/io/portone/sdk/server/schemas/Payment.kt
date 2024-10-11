package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * 결제 건
 */
@Serializable
@JsonClassDiscriminator("status")
public sealed interface Payment {
  /**
   * 결제 건 아이디
   */
  public val id: String

  /**
   * 고객사 아이디
   */
  public val merchantId: String

  /**
   * 상점 아이디
   */
  public val storeId: String

  /**
   * 결제수단 정보
   */
  public val method: PaymentMethod?

  /**
   * 결제 채널
   */
  public val channel: SelectedChannel?

  /**
   * 결제 채널 그룹 정보
   */
  public val channelGroup: ChannelGroupSummary?

  /**
   * 포트원 버전
   */
  public val version: PortOneVersion

  /**
   * 결제 예약 건 아이디
   *
   * 결제 예약을 이용한 경우에만 존재
   */
  public val scheduleId: String?

  /**
   * 결제 시 사용된 빌링키
   *
   * 빌링키 결제인 경우에만 존재
   */
  public val billingKey: String?

  /**
   * 웹훅 발송 내역
   */
  public val webhooks: List<PaymentWebhook>?

  /**
   * 결제 요청 시점
   */
  public val requestedAt: @Serializable(InstantSerializer::class) Instant

  /**
   * 업데이트 시점
   */
  public val updatedAt: @Serializable(InstantSerializer::class) Instant

  /**
   * 상태 업데이트 시점
   */
  public val statusChangedAt: @Serializable(InstantSerializer::class) Instant

  /**
   * 주문명
   */
  public val orderName: String

  /**
   * 결제 금액 관련 세부 정보
   */
  public val amount: PaymentAmount

  /**
   * 통화
   */
  public val currency: Currency

  /**
   * 구매자 정보
   */
  public val customer: Customer

  /**
   * 프로모션 아이디
   */
  public val promotionId: String?

  /**
   * 문화비 지출 여부
   */
  public val isCulturalExpense: Boolean?

  /**
   * 에스크로 결제 정보
   *
   * 에스크로 결제인 경우 존재합니다.
   */
  public val escrow: PaymentEscrow?

  /**
   * 상품 정보
   */
  public val products: List<PaymentProduct>?

  /**
   * 상품 갯수
   */
  public val productCount: Int?

  /**
   * 사용자 지정 데이터
   */
  public val customData: String?

  /**
   * 국가 코드
   */
  public val country: Country?
}
