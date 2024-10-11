package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제 건이 존재하지 않는 경우
 */
public class PaymentNotFoundException(
  message: String? = null,
) : Exception(message)
