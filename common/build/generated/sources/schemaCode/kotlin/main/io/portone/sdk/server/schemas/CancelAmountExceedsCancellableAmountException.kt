package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제 취소 금액이 취소 가능 금액을 초과한 경우
 */
public class CancelAmountExceedsCancellableAmountException(
  message: String? = null,
) : Exception(message)
