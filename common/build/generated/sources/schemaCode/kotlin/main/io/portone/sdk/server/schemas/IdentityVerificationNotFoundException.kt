package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 요청된 본인인증 건이 존재하지 않는 경우
 */
public class IdentityVerificationNotFoundException(
  message: String? = null,
) : Exception(message)
