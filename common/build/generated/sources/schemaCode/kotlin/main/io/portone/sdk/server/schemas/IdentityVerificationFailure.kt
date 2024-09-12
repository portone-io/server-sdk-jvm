package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 본인인증 실패 정보
 */
@Serializable
public data class IdentityVerificationFailure(
  /**
   * 실패 사유
   */
  public val reason: String? = null,
  /**
   * PG사 실패 코드
   */
  public val pgCode: String? = null,
  /**
   * PG사 실패 메시지
   */
  public val pgMessage: String? = null,
)
