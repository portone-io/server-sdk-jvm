package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * 본인인증 내역
 */
@Serializable
@JsonClassDiscriminator("status")
public sealed interface IdentityVerification {
  /**
   * 본인인증 내역 아이디
   */
  public val id: String

  /**
   * 사용된 본인인증 채널
   */
  public val channel: SelectedChannel?

  /**
   * 사용자 지정 데이터
   */
  public val customData: String?

  /**
   * 본인인증 요청 시점
   */
  public val requestedAt: @Serializable(InstantSerializer::class) Instant

  /**
   * 업데이트 시점
   */
  public val updatedAt: @Serializable(InstantSerializer::class) Instant

  /**
   * 상태 업데이트 시점
   */
  public val statusChangedAt: @Serializable(InstantSerializer::class) Instant
}
