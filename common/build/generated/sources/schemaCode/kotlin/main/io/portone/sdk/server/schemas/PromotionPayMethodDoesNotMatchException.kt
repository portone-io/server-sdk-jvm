package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제수단이 프로모션에 지정된 것과 일치하지 않는 경우
 */
public class PromotionPayMethodDoesNotMatchException(
  message: String? = null,
) : Exception(message)
