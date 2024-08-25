package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * BillingKeyNotIssuedError
 */
public class BillingKeyNotIssuedException(
  message: String? = null,
) : Exception(message)
