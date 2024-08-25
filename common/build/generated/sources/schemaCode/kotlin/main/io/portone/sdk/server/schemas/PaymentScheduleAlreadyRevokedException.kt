package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제 예약건이 이미 취소된 경우
 */
public class PaymentScheduleAlreadyRevokedException(
  message: String? = null,
) : Exception(message)
