package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 요청이 거절된 경우
 */
public class ForbiddenException(
  message: String? = null,
) : Exception(message)
