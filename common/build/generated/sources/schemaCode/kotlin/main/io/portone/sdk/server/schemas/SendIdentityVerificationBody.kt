package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * 본인인증 요청을 위한 입력 정보
 */
@Serializable
internal data class SendIdentityVerificationBody(
  /**
   * 상점 아이디
   *
   * 접근 권한이 있는 상점 아이디만 입력 가능하며, 미입력시 토큰에 담긴 상점 아이디를 사용합니다.
   */
  public val storeId: String? = null,
  /**
   * 채널 키
   */
  public val channelKey: String,
  /**
   * 고객 정보
   */
  public val customer: SendIdentityVerificationBodyCustomer,
  /**
   * 사용자 지정 데이터
   */
  public val customData: String? = null,
  /**
   * PG사별 추가 파라미터 ("PG사별 연동 가이드" 참고)
   */
  public val bypass: JsonObject? = null,
  /**
   * 통신사
   */
  public val `operator`: IdentityVerificationOperator,
  /**
   * 본인인증 방식
   */
  public val method: IdentityVerificationMethod,
)
