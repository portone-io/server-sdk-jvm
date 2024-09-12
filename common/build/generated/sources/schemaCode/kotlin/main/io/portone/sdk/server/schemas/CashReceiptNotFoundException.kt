package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 현금영수증이 존재하지 않는 경우
 */
public class CashReceiptNotFoundException(
  message: String? = null,
) : Exception(message)
