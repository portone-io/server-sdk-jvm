package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 빌링키가 존재하지 않는 경우
 */
public class BillingKeyNotFoundException(
  message: String? = null,
) : Exception(message)
