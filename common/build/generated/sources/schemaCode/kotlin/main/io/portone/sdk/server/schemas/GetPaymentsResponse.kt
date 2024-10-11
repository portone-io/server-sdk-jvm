package io.portone.sdk.server.schemas

import kotlin.collections.List
import kotlinx.serialization.Serializable

/**
 * 결제 건 다건 조회 성공 응답 정보
 */
@Serializable
public data class GetPaymentsResponse(
  /**
   * 조회된 결제 건 리스트
   */
  public val items: List<Payment>,
  /**
   * 조회된 페이지 정보
   */
  public val page: PageInfo,
)
