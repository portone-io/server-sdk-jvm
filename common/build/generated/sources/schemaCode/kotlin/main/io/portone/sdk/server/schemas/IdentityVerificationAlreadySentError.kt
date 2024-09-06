package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 본인인증 건이 이미 API로 요청된 상태인 경우
 */
@Serializable
@SerialName("IDENTITY_VERIFICATION_ALREADY_SENT")
internal data class IdentityVerificationAlreadySentError(
  override val message: String? = null,
) : SendIdentityVerificationError
