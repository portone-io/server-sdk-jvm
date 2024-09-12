package io.portone.sdk.server.schemas

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * 빌링키 결제 요청 입력 정보
 */
@Serializable
public data class BillingKeyPaymentInput(
  /**
   * 상점 아이디
   *
   * 접근 권한이 있는 상점 아이디만 입력 가능하며, 미입력시 토큰에 담긴 상점 아이디를 사용합니다.
   */
  public val storeId: String? = null,
  /**
   * 빌링키 결제에 사용할 빌링키
   */
  public val billingKey: String,
  /**
   * 채널 키
   *
   * 다수 채널에 대해 발급된 빌링키에 대해, 결제 채널을 특정하고 싶을 때 명시
   */
  public val channelKey: String? = null,
  /**
   * 주문명
   */
  public val orderName: String,
  /**
   * 고객 정보
   */
  public val customer: CustomerInput? = null,
  /**
   * 사용자 지정 데이터
   */
  public val customData: String? = null,
  /**
   * 결제 금액 세부 입력 정보
   */
  public val amount: PaymentAmountInput,
  /**
   * 통화
   */
  public val currency: Currency,
  /**
   * 할부 개월 수
   */
  public val installmentMonth: Int? = null,
  /**
   * 무이자 할부 이자를 고객사가 부담할지 여부
   */
  public val useFreeInterestFromMerchant: Boolean? = null,
  /**
   * 카드 포인트 사용 여부
   */
  public val useCardPoint: Boolean? = null,
  /**
   * 현금영수증 정보
   */
  public val cashReceipt: CashReceiptInput? = null,
  /**
   * 결제 국가
   */
  public val country: Country? = null,
  /**
   * 웹훅 주소
   *
   * 결제 승인/실패 시 요청을 받을 웹훅 주소입니다.
   * 상점에 설정되어 있는 값보다 우선적으로 적용됩니다.
   * 입력된 값이 없을 경우에는 빈 배열로 해석됩니다.
   */
  public val noticeUrls: List<String>? = null,
  /**
   * 상품 정보
   *
   * 입력된 값이 없을 경우에는 빈 배열로 해석됩니다.
   */
  public val products: List<PaymentProduct>? = null,
  /**
   * 상품 개수
   */
  public val productCount: Int? = null,
  /**
   * 상품 유형
   */
  public val productType: PaymentProductType? = null,
  /**
   * 배송지 주소
   */
  public val shippingAddress: SeparatedAddressInput? = null,
  /**
   * 해당 결제에 적용할 프로모션 아이디
   */
  public val promotionId: String? = null,
  /**
   * PG사별 추가 파라미터 ("PG사별 연동 가이드" 참고)
   */
  public val bypass: JsonObject? = null,
)
