package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 통합검색 입력 정보
 */
@Serializable
public data class BillingKeyTextSearch(
  public val `field`: BillingKeyTextSearchField,
  public val `value`: String,
)
