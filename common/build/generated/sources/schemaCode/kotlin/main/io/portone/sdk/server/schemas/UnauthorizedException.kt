package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 인증 정보가 올바르지 않은 경우
 */
public class UnauthorizedException(
  message: String? = null,
) : Exception(message)
