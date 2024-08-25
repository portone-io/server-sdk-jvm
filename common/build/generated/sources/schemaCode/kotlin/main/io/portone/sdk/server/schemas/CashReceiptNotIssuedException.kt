package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 현금영수증이 발급되지 않은 경우
 */
public class CashReceiptNotIssuedException(
  message: String? = null,
) : Exception(message)
