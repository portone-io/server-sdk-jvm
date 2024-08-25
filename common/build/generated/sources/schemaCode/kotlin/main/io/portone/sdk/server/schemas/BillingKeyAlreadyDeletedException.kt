package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 빌링키가 이미 삭제된 경우
 */
public class BillingKeyAlreadyDeletedException(
  message: String? = null,
) : Exception(message)
