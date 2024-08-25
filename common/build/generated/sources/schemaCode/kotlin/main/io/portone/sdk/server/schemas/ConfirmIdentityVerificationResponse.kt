package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 본인인증 확인 성공 응답
 */
@Serializable
public data class ConfirmIdentityVerificationResponse(
  /**
   * 완료된 본인인증 내역
   */
  public val identityVerification: VerifiedIdentityVerification,
)
