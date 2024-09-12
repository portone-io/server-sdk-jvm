package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 분리 형식 주소 입력 정보
 */
@Serializable
public data class SeparatedAddressInput(
  /**
   * 상세 주소 1
   */
  public val addressLine1: String,
  /**
   * 상세 주소 2
   */
  public val addressLine2: String,
  /**
   * 시/군/구
   */
  public val city: String? = null,
  /**
   * 주/도/시
   */
  public val province: String? = null,
  /**
   * 국가
   */
  public val country: Country? = null,
)
