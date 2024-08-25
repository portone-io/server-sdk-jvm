package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 웹훅 내역이 존재하지 않는 경우
 */
public class WebhookNotFoundException(
  message: String? = null,
) : Exception(message)
