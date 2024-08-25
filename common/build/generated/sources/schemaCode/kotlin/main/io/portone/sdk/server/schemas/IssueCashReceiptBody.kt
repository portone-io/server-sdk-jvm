package io.portone.sdk.server.schemas

import io.portone.sdk.server.serializers.InstantSerializer
import java.time.Instant
import kotlin.String
import kotlinx.serialization.Serializable

/**
 * 현금영수증 발급 요청 양식
 */
@Serializable
internal data class IssueCashReceiptBody(
  /**
   * 상점 아이디
   *
   * 접근 권한이 있는 상점 아이디만 입력 가능하며, 미입력시 토큰에 담긴 상점 아이디를 사용합니다.
   */
  public val storeId: String? = null,
  /**
   * 결제 건 아이디
   *
   * 외부 결제 건에 대한 수동 발급의 경우, 아이디를 직접 채번하여 입력합니다.
   */
  public val paymentId: String,
  /**
   * 채널 키
   */
  public val channelKey: String,
  /**
   * 현금 영수증 유형
   */
  public val type: CashReceiptType,
  /**
   * 주문명
   */
  public val orderName: String,
  /**
   * 화폐
   */
  public val currency: Currency,
  /**
   * 금액 세부 입력 정보
   */
  public val amount: PaymentAmountInput,
  /**
   * 상품 유형
   */
  public val productType: PaymentProductType? = null,
  /**
   * 고객 정보
   */
  public val customer: IssueCashReceiptCustomerInput,
  /**
   * 결제 일자
   */
  public val paidAt: @Serializable(InstantSerializer::class) Instant? = null,
)
