package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 결제 예약 건 다건 조회 시 정렬 조건
 */
@Serializable
public data class PaymentScheduleSortInput(
  /**
   * 정렬 기준 필드
   *
   * 어떤 필드를 기준으로 정렬할 지 결정합니다. 비워서 보낼 경우, TIME_TO_PAY가 기본값으로 설정됩니다.
   */
  public val `by`: PaymentScheduleSortBy? = null,
  /**
   * 정렬 순서
   *
   * 어떤 순서로 정렬할 지 결정합니다. 비워서 보낼 경우, DESC(내림차순)가 기본값으로 설정됩니다.
   */
  public val order: SortOrder? = null,
)
