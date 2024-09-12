package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * 요청된 채널이 존재하지 않는 경우
 */
public class ChannelNotFoundException(
  message: String? = null,
) : Exception(message)
