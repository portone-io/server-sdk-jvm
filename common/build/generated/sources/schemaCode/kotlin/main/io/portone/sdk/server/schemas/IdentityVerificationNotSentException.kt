package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 본인인증 건이 API로 요청된 상태가 아닌 경우
 */
public class IdentityVerificationNotSentException(
  message: String? = null,
) : Exception(message)
