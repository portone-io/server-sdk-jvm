package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.Boolean
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

/**
 * 결제 건 다건 조회를 위한 입력 정보
 */
@Serializable
public data class PaymentFilterInput(
  /**
   * 고객사 아이디
   */
  public val merchantId: String? = null,
  /**
   * 상점 아이디
   *
   * Merchant 사용자만 사용가능하며, 지정되지 않은 경우 고객사 전체 결제 건을 조회합니다.
   */
  public val storeId: String? = null,
  /**
   * 조회 기준 시점 유형
   */
  public val timestampType: PaymentTimestampType? = null,
  /**
   * 결제 요청/상태 승인 시점 범위의 시작
   *
   * 값을 입력하지 않으면 end의 90일 전으로 설정됩니다.
   */
  public val from: @Serializable(InstantSerializer::class) Instant? = null,
  /**
   * 결제 요청/상태 승인 시점 범위의 끝
   *
   * 값을 입력하지 않으면 현재 시점으로 설정됩니다.
   */
  public val until: @Serializable(InstantSerializer::class) Instant? = null,
  /**
   * 결제 상태 리스트
   *
   * 값을 입력하지 않으면 결제상태 필터링이 적용되지 않습니다.
   */
  public val status: List<PaymentStatus>? = null,
  /**
   * 결제수단 리스트
   *
   * 값을 입력하지 않으면 결제수단 필터링이 적용되지 않습니다.
   */
  public val methods: List<PaymentMethodType>? = null,
  /**
   * PG사 리스트
   *
   * 값을 입력하지 않으면 결제대행사 필터링이 적용되지 않습니다.
   */
  public val pgProvider: List<PgProvider>? = null,
  /**
   * 테스트 결제 필터링
   */
  public val isTest: Boolean? = null,
  /**
   * 결제 예약 건 필터링
   */
  public val isScheduled: Boolean? = null,
  /**
   * 결제 건 정렬 기준
   */
  public val sortBy: PaymentSortBy? = null,
  /**
   * 결제 건 정렬 방식
   */
  public val sortOrder: SortOrder? = null,
  /**
   * 포트원 버전
   */
  public val version: PortOneVersion? = null,
  /**
   * 웹훅 상태
   */
  public val webhookStatus: PaymentWebhookStatus? = null,
  /**
   * 플랫폼 유형
   */
  public val platformType: PaymentClientType? = null,
  /**
   * 통화
   */
  public val currency: Currency? = null,
  /**
   * 에스크로 결제 여부
   */
  public val isEscrow: Boolean? = null,
  /**
   * 에스크로 결제의 배송 정보 상태
   */
  public val escrowStatus: PaymentFilterInputEscrowStatus? = null,
  /**
   * 카드 브랜드
   */
  public val cardBrand: CardBrand? = null,
  /**
   * 카드 유형
   */
  public val cardType: CardType? = null,
  /**
   * 카드 소유주 유형
   */
  public val cardOwnerType: CardOwnerType? = null,
  /**
   * 상품권 종류
   */
  public val giftCertificateType: PaymentMethodGiftCertificateType? = null,
  /**
   * 현금영수증 유형
   */
  public val cashReceiptType: CashReceiptInputType? = null,
  /**
   * 현금영수증 상태
   */
  public val cashReceiptStatus: PaymentCashReceiptStatus? = null,
  /**
   * 현금영수증 발급 시간 범위
   */
  public val cashReceiptIssuedAtRange: DateTimeRange? = null,
  /**
   * 현금영수증 취소 시간 범위
   */
  public val cashReceiptCancelledAtRange: DateTimeRange? = null,
  /**
   * 통합 검색 리스트 필터
   */
  public val textSearch: List<PaymentTextSearch>? = null,
)
