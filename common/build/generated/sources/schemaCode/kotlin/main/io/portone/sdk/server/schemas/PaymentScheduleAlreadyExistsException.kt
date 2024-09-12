package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제 예약건이 이미 존재하는 경우
 */
public class PaymentScheduleAlreadyExistsException(
  message: String? = null,
) : Exception(message)
