package io.portone.sdk.server.schemas

import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 고객 정보 입력 정보
 */
@Serializable
public data class CustomerInput(
  /**
   * 고객 아이디
   *
   * 고객사가 지정한 고객의 고유 식별자입니다.
   */
  public val id: String? = null,
  /**
   * 이름
   */
  public val name: CustomerNameInput? = null,
  /**
   * 출생 연도
   */
  public val birthYear: String? = null,
  /**
   * 출생월
   */
  public val birthMonth: String? = null,
  /**
   * 출생일
   */
  public val birthDay: String? = null,
  /**
   * 국가
   */
  public val country: Country? = null,
  /**
   * 성별
   */
  public val gender: Gender? = null,
  /**
   * 이메일
   */
  public val email: String? = null,
  /**
   * 전화번호
   */
  public val phoneNumber: String? = null,
  /**
   * 주소
   */
  public val address: SeparatedAddressInput? = null,
  /**
   * 우편번호
   */
  public val zipcode: String? = null,
  /**
   * 사업자 등록 번호
   */
  public val businessRegistrationNumber: String? = null,
)
