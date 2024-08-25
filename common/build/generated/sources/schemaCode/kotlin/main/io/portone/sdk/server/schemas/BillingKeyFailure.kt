package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 발급 실패 상세 정보
 */
@Serializable
public data class BillingKeyFailure(
  /**
   * 실패 사유
   */
  public val message: String? = null,
  /**
   * PG사 실패 코드
   */
  public val pgCode: String? = null,
  /**
   * PG사 실패 사유
   */
  public val pgMessage: String? = null,
  /**
   * 실패 시점
   */
  public val failedAt: @Serializable(InstantSerializer::class) Instant,
)
