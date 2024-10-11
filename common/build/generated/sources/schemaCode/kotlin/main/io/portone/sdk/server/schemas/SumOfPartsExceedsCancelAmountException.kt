package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 면세 금액 등 하위 항목들의 합이 전체 취소 금액을 초과한 경우
 */
public class SumOfPartsExceedsCancelAmountException(
  message: String? = null,
) : Exception(message)
