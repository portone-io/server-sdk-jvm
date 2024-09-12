package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 결제가 이미 완료되었거나 대기중인 경우
 */
public class AlreadyPaidOrWaitingException(
  message: String? = null,
) : Exception(message)
