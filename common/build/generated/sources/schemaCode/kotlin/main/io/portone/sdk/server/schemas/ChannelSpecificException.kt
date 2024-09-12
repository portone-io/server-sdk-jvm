package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String
import kotlin.collections.List

/**
 * 여러 채널을 지정한 요청에서, 채널 각각에서 오류가 발생한 경우
 */
public class ChannelSpecificException(
  message: String? = null,
  public val failures: List<ChannelSpecificFailure>,
  /**
   * (결제, 본인인증 등에) 선택된 채널 정보
   */
  public val succeededChannels: List<SelectedChannel>,
) : Exception(message)
