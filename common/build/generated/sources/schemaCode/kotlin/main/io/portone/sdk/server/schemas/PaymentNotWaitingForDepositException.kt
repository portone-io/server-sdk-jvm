package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제 건이 입금 대기 상태가 아닌 경우
 */
public class PaymentNotWaitingForDepositException(
  message: String? = null,
) : Exception(message)
