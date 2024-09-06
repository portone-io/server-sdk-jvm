package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * GetPaymentsBody
 *
 * 결제 건 다건 조회를 위한 입력 정보
 */
@Serializable
internal data class GetPaymentsBody(
  /**
   * 요청할 페이지 정보
   *
   * 미 입력 시 number: 0, size: 10 으로 기본값이 적용됩니다.
   */
  public val page: PageInput? = null,
  /**
   * 조회할 결제 건 조건 필터
   *
   * V1 결제 건의 경우 일부 필드에 대해 필터가 적용되지 않을 수 있습니다.
   */
  public val filter: PaymentFilterInput? = null,
)
