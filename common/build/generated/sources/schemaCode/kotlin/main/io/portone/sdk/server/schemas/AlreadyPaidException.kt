package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제가 이미 완료된 경우
 */
public class AlreadyPaidException(
  message: String? = null,
) : Exception(message)
