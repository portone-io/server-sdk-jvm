package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.Int
import kotlinx.serialization.Serializable

/**
 * 입금 만료 기한
 *
 * validHours와 dueDate 둘 중 하나의 필드만 입력합니다.
 */
@Serializable
public data class InstantPaymentMethodInputVirtualAccountExpiry(
  /**
   * 유효 시간
   *
   * 시간 단위로 입력합니다.
   */
  public val validHours: Int? = null,
  /**
   * 만료 시점
   */
  public val dueDate: @Serializable(InstantSerializer::class) Instant? = null,
)
