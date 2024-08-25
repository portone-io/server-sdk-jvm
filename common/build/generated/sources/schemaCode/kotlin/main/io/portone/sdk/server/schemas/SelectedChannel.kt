package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * (결제, 본인인증 등에) 선택된 채널 정보
 */
@Serializable
public data class SelectedChannel(
  /**
   * 채널 타입
   */
  public val type: SelectedChannelType,
  /**
   * 채널 아이디
   */
  public val id: String? = null,
  /**
   * 채널 키
   */
  public val key: String? = null,
  /**
   * 채널 명
   */
  public val name: String? = null,
  /**
   * PG사
   */
  public val pgProvider: PgProvider,
  /**
   * PG사 고객사 식별 아이디
   */
  public val pgMerchantId: String,
)
