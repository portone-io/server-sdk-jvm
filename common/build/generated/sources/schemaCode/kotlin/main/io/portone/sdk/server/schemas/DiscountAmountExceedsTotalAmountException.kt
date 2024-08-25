package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 프로모션 할인 금액이 결제 시도 금액 이상인 경우
 */
public class DiscountAmountExceedsTotalAmountException(
  message: String? = null,
) : Exception(message)
