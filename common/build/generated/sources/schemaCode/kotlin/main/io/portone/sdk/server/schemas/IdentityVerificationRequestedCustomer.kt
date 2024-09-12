package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 요청 시 고객 정보
 */
@Serializable
public data class IdentityVerificationRequestedCustomer(
  /**
   * 식별 아이디
   */
  public val id: String? = null,
  /**
   * 이름
   */
  public val name: String? = null,
  /**
   * 전화번호
   *
   * 특수 문자(-) 없이 숫자로만 이루어진 번호 형식입니다.
   */
  public val phoneNumber: String? = null,
)
