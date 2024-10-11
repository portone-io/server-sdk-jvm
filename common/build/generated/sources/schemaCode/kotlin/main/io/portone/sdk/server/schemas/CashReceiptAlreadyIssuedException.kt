package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 현금영수증이 이미 발급된 경우
 */
public class CashReceiptAlreadyIssuedException(
  message: String? = null,
) : Exception(message)
