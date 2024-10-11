package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제가 이미 취소된 경우
 */
public class PaymentAlreadyCancelledException(
  message: String? = null,
) : Exception(message)
