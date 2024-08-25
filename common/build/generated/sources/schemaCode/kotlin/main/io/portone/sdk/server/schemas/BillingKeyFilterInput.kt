package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

/**
 * 빌링키 다건 조회를 위한 입력 정보
 */
@Serializable
public data class BillingKeyFilterInput(
  /**
   * 상점 아이디
   *
   * Merchant 사용자만 사용가능하며, 지정되지 않은 경우 고객사 전체 빌링키를 조회합니다.
   */
  public val storeId: String? = null,
  /**
   * 조회 기준 시점 유형
   */
  public val timeRangeField: BillingKeyTimeRangeField? = null,
  /**
   * 조회 기준 시점 범위의 시작
   *
   * 값을 입력하지 않으면 end의 90일 전으로 설정됩니다.
   */
  public val from: @Serializable(InstantSerializer::class) Instant? = null,
  /**
   * 조회 기준 시점 범위의 끝
   *
   * 값을 입력하지 않으면 현재 시점으로 설정됩니다.
   */
  public val until: @Serializable(InstantSerializer::class) Instant? = null,
  /**
   * 빌링키 상태 리스트
   *
   * 값을 입력하지 않으면 빌링키 상태 필터링이 적용되지 않습니다.
   */
  public val status: List<BillingKeyStatus>? = null,
  /**
   * 채널 그룹 아이디 리스트
   *
   * 값을 입력하지 않으면 스마트 라우팅 그룹 아이디 필터링이 적용되지 않습니다.
   */
  public val channelGroupIds: List<String>? = null,
  /**
   * 고객 ID
   */
  public val customerId: String? = null,
  /**
   * 플랫폼 유형
   */
  public val platformType: PaymentClientType? = null,
  /**
   * 통합 검색 필터
   */
  public val textSearch: BillingKeyTextSearch? = null,
  /**
   * PG사 결제 모듈 리스트
   *
   * 값을 입력하지 않으면 PG사 결제 모듈 필터링이 적용되지 않습니다.
   */
  public val pgProviders: List<PgProvider>? = null,
  /**
   * PG사 리스트
   *
   * 값을 입력하지 않으면 PG사 필터링이 적용되지 않습니다.
   */
  public val pgCompanies: List<PgCompany>? = null,
  /**
   * 결제수단 리스트
   *
   * 값을 입력하지 않으면 결제수단 필터링이 적용되지 않습니다.
   */
  public val methods: List<BillingKeyPaymentMethodType>? = null,
  /**
   * 포트원 버전
   */
  public val version: PortOneVersion? = null,
)
