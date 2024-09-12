package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 부분 취소 시, 취소하게 될 경우 남은 금액이 프로모션의 최소 결제 금액보다 작아지는 경우
 */
public class RemainedAmountLessThanPromotionMinPaymentAmountException(
  message: String? = null,
) : Exception(message)
