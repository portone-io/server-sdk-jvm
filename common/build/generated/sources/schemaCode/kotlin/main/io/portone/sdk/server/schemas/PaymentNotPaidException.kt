package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제가 완료되지 않은 경우
 */
public class PaymentNotPaidException(
  message: String? = null,
) : Exception(message)
