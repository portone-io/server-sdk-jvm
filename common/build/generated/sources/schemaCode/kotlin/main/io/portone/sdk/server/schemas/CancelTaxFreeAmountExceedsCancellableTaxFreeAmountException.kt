package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 취소 면세 금액이 취소 가능한 면세 금액을 초과한 경우
 */
public class CancelTaxFreeAmountExceedsCancellableTaxFreeAmountException(
  message: String? = null,
) : Exception(message)
