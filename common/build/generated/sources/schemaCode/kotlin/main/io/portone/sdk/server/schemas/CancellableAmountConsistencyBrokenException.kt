package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 취소 가능 잔액 검증에 실패한 경우
 */
public class CancellableAmountConsistencyBrokenException(
  message: String? = null,
) : Exception(message)
