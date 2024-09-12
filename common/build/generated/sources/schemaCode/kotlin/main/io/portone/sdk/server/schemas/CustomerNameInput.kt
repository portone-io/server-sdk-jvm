package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 고객 이름 입력 정보
 *
 * 두 개의 이름 형식 중 한 가지만 선택하여 입력해주세요.
 */
@Serializable
public data class CustomerNameInput(
  /**
   * 한 줄 이름 형식
   */
  public val full: String? = null,
  /**
   * 분리형 이름 형식
   */
  public val separated: CustomerSeparatedName? = null,
)
