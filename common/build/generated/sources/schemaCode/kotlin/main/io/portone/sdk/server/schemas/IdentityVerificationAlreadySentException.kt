package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 본인인증 건이 이미 API로 요청된 상태인 경우
 */
public class IdentityVerificationAlreadySentException(
  message: String? = null,
) : Exception(message)
