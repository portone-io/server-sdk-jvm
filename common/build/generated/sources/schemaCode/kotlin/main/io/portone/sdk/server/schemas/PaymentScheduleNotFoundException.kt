package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제 예약건이 존재하지 않는 경우
 */
public class PaymentScheduleNotFoundException(
  message: String? = null,
) : Exception(message)
