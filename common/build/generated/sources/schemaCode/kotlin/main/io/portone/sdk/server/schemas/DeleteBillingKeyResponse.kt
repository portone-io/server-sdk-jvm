package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlinx.serialization.Serializable

/**
 * 빌링키 삭제 성공 응답
 */
@Serializable
public data class DeleteBillingKeyResponse(
  /**
   * 빌링키 삭제 완료 시점
   */
  public val deletedAt: @Serializable(InstantSerializer::class) Instant,
)
