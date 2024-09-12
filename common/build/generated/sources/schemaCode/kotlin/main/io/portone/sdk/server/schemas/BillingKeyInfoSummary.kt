package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable

/**
 * BillingKeyInfoSummary
 */
@Serializable
public data class BillingKeyInfoSummary(
  /**
   * 발급된 빌링키
   */
  public val billingKey: String,
  /**
   * (결제, 본인인증 등에) 선택된 채널 정보
   */
  public val channels: List<SelectedChannel>? = null,
  /**
   * 빌링크 발급 완료 시점
   */
  public val issuedAt: @Serializable(InstantSerializer::class) Instant,
)
