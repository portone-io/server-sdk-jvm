package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 고객 분리형 이름
 */
@Serializable
public data class CustomerSeparatedName(
  /**
   * 이름
   */
  public val first: String,
  /**
   * 성
   */
  public val last: String,
)
