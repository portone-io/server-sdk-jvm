package io.portone.sdk.server.schemas

import java.lang.Exception
import kotlin.String

/**
 * PG사에서 오류를 전달한 경우
 */
public class PgProviderException(
  message: String? = null,
  public val pgCode: String,
  public val pgMessage: String,
) : Exception(message)
