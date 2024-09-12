package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 분리 형식 주소
 *
 * 한 줄 형식 주소와 분리 형식 주소 모두 존재합니다.
 * 한 줄 형식 주소는 분리 형식 주소를 이어 붙인 형태로 생성됩니다.
 */
@Serializable
@SerialName("SEPARATED")
public data class SeparatedAddress(
  /**
   * 주소 (한 줄)
   */
  override val oneLine: String,
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
) : Address
