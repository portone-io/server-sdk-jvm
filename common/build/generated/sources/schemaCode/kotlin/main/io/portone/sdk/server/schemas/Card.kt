package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 카드 상세 정보
 */
@Serializable
public data class Card(
  /**
   * 발행사 코드
   */
  public val publisher: String? = null,
  /**
   * 발급사 코드
   */
  public val issuer: String? = null,
  /**
   * 카드 브랜드
   */
  public val brand: CardBrand? = null,
  /**
   * 카드 유형
   */
  public val type: CardType? = null,
  /**
   * 카드 소유주 유형
   */
  public val ownerType: CardOwnerType? = null,
  /**
   * 카드 번호 앞 6자리 또는 8자리의 BIN (Bank Identification Number)
   */
  public val bin: String? = null,
  /**
   * 카드 상품명
   */
  public val name: String? = null,
  /**
   * 마스킹된 카드 번호
   */
  public val number: String? = null,
)
