package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 본인인증 방식
 */
@Serializable
public enum class IdentityVerificationMethod {
  SMS,
  APP,
}
