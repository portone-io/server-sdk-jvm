package io.portone.sdk.server

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.`get`
import io.ktor.client.request.accept
import io.ktor.client.request.delete
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.userAgent
import io.portone.sdk.server.schemas.AlreadyPaidError
import io.portone.sdk.server.schemas.AlreadyPaidException
import io.portone.sdk.server.schemas.AlreadyPaidOrWaitingError
import io.portone.sdk.server.schemas.AlreadyPaidOrWaitingException
import io.portone.sdk.server.schemas.ApplyEscrowLogisticsError
import io.portone.sdk.server.schemas.ApplyEscrowLogisticsResponse
import io.portone.sdk.server.schemas.BillingKeyAlreadyDeletedError
import io.portone.sdk.server.schemas.BillingKeyAlreadyDeletedException
import io.portone.sdk.server.schemas.BillingKeyFilterInput
import io.portone.sdk.server.schemas.BillingKeyInfo
import io.portone.sdk.server.schemas.BillingKeyNotFoundError
import io.portone.sdk.server.schemas.BillingKeyNotFoundException
import io.portone.sdk.server.schemas.BillingKeyNotIssuedError
import io.portone.sdk.server.schemas.BillingKeyNotIssuedException
import io.portone.sdk.server.schemas.BillingKeyPaymentInput
import io.portone.sdk.server.schemas.BillingKeySortInput
import io.portone.sdk.server.schemas.CancelAmountExceedsCancellableAmountError
import io.portone.sdk.server.schemas.CancelAmountExceedsCancellableAmountException
import io.portone.sdk.server.schemas.CancelCashReceiptError
import io.portone.sdk.server.schemas.CancelCashReceiptResponse
import io.portone.sdk.server.schemas.CancelPaymentBody
import io.portone.sdk.server.schemas.CancelPaymentBodyRefundAccount
import io.portone.sdk.server.schemas.CancelPaymentError
import io.portone.sdk.server.schemas.CancelPaymentResponse
import io.portone.sdk.server.schemas.CancelRequester
import io.portone.sdk.server.schemas.CancelTaxAmountExceedsCancellableTaxAmountError
import io.portone.sdk.server.schemas.CancelTaxAmountExceedsCancellableTaxAmountException
import io.portone.sdk.server.schemas.CancelTaxFreeAmountExceedsCancellableTaxFreeAmountError
import io.portone.sdk.server.schemas.CancelTaxFreeAmountExceedsCancellableTaxFreeAmountException
import io.portone.sdk.server.schemas.CancellableAmountConsistencyBrokenError
import io.portone.sdk.server.schemas.CancellableAmountConsistencyBrokenException
import io.portone.sdk.server.schemas.CashReceipt
import io.portone.sdk.server.schemas.CashReceiptAlreadyIssuedError
import io.portone.sdk.server.schemas.CashReceiptAlreadyIssuedException
import io.portone.sdk.server.schemas.CashReceiptInput
import io.portone.sdk.server.schemas.CashReceiptNotFoundError
import io.portone.sdk.server.schemas.CashReceiptNotFoundException
import io.portone.sdk.server.schemas.CashReceiptNotIssuedError
import io.portone.sdk.server.schemas.CashReceiptNotIssuedException
import io.portone.sdk.server.schemas.CashReceiptType
import io.portone.sdk.server.schemas.ChannelNotFoundError
import io.portone.sdk.server.schemas.ChannelNotFoundException
import io.portone.sdk.server.schemas.ChannelSpecificError
import io.portone.sdk.server.schemas.ChannelSpecificException
import io.portone.sdk.server.schemas.CloseVirtualAccountError
import io.portone.sdk.server.schemas.CloseVirtualAccountResponse
import io.portone.sdk.server.schemas.ConfirmEscrowBody
import io.portone.sdk.server.schemas.ConfirmEscrowError
import io.portone.sdk.server.schemas.ConfirmEscrowResponse
import io.portone.sdk.server.schemas.ConfirmIdentityVerificationBody
import io.portone.sdk.server.schemas.ConfirmIdentityVerificationError
import io.portone.sdk.server.schemas.ConfirmIdentityVerificationResponse
import io.portone.sdk.server.schemas.Country
import io.portone.sdk.server.schemas.CreatePaymentScheduleBody
import io.portone.sdk.server.schemas.CreatePaymentScheduleError
import io.portone.sdk.server.schemas.CreatePaymentScheduleResponse
import io.portone.sdk.server.schemas.Currency
import io.portone.sdk.server.schemas.CustomerInput
import io.portone.sdk.server.schemas.DeleteBillingKeyError
import io.portone.sdk.server.schemas.DeleteBillingKeyResponse
import io.portone.sdk.server.schemas.DiscountAmountExceedsTotalAmountError
import io.portone.sdk.server.schemas.DiscountAmountExceedsTotalAmountException
import io.portone.sdk.server.schemas.ForbiddenError
import io.portone.sdk.server.schemas.ForbiddenException
import io.portone.sdk.server.schemas.GetAllPaymentsByCursorBody
import io.portone.sdk.server.schemas.GetAllPaymentsByCursorResponse
import io.portone.sdk.server.schemas.GetAllPaymentsError
import io.portone.sdk.server.schemas.GetBillingKeyInfoError
import io.portone.sdk.server.schemas.GetBillingKeyInfosBody
import io.portone.sdk.server.schemas.GetBillingKeyInfosError
import io.portone.sdk.server.schemas.GetBillingKeyInfosResponse
import io.portone.sdk.server.schemas.GetCashReceiptError
import io.portone.sdk.server.schemas.GetIdentityVerificationError
import io.portone.sdk.server.schemas.GetKakaopayPaymentOrderError
import io.portone.sdk.server.schemas.GetKakaopayPaymentOrderResponse
import io.portone.sdk.server.schemas.GetPaymentError
import io.portone.sdk.server.schemas.GetPaymentScheduleError
import io.portone.sdk.server.schemas.GetPaymentSchedulesBody
import io.portone.sdk.server.schemas.GetPaymentSchedulesError
import io.portone.sdk.server.schemas.GetPaymentSchedulesResponse
import io.portone.sdk.server.schemas.GetPaymentsBody
import io.portone.sdk.server.schemas.GetPaymentsError
import io.portone.sdk.server.schemas.GetPaymentsResponse
import io.portone.sdk.server.schemas.IdentityVerification
import io.portone.sdk.server.schemas.IdentityVerificationAlreadySentError
import io.portone.sdk.server.schemas.IdentityVerificationAlreadySentException
import io.portone.sdk.server.schemas.IdentityVerificationAlreadyVerifiedError
import io.portone.sdk.server.schemas.IdentityVerificationAlreadyVerifiedException
import io.portone.sdk.server.schemas.IdentityVerificationMethod
import io.portone.sdk.server.schemas.IdentityVerificationNotFoundError
import io.portone.sdk.server.schemas.IdentityVerificationNotFoundException
import io.portone.sdk.server.schemas.IdentityVerificationNotSentError
import io.portone.sdk.server.schemas.IdentityVerificationNotSentException
import io.portone.sdk.server.schemas.IdentityVerificationOperator
import io.portone.sdk.server.schemas.InstantBillingKeyPaymentMethodInput
import io.portone.sdk.server.schemas.InstantPaymentInput
import io.portone.sdk.server.schemas.InstantPaymentMethodInput
import io.portone.sdk.server.schemas.InvalidRequestError
import io.portone.sdk.server.schemas.InvalidRequestException
import io.portone.sdk.server.schemas.IssueBillingKeyBody
import io.portone.sdk.server.schemas.IssueBillingKeyError
import io.portone.sdk.server.schemas.IssueBillingKeyResponse
import io.portone.sdk.server.schemas.IssueCashReceiptBody
import io.portone.sdk.server.schemas.IssueCashReceiptCustomerInput
import io.portone.sdk.server.schemas.IssueCashReceiptError
import io.portone.sdk.server.schemas.IssueCashReceiptResponse
import io.portone.sdk.server.schemas.ModifyEscrowLogisticsBody
import io.portone.sdk.server.schemas.ModifyEscrowLogisticsError
import io.portone.sdk.server.schemas.ModifyEscrowLogisticsResponse
import io.portone.sdk.server.schemas.PageInput
import io.portone.sdk.server.schemas.PayInstantlyError
import io.portone.sdk.server.schemas.PayInstantlyResponse
import io.portone.sdk.server.schemas.PayWithBillingKeyError
import io.portone.sdk.server.schemas.PayWithBillingKeyResponse
import io.portone.sdk.server.schemas.Payment
import io.portone.sdk.server.schemas.PaymentAlreadyCancelledError
import io.portone.sdk.server.schemas.PaymentAlreadyCancelledException
import io.portone.sdk.server.schemas.PaymentAmountInput
import io.portone.sdk.server.schemas.PaymentEscrowReceiverInput
import io.portone.sdk.server.schemas.PaymentEscrowSenderInput
import io.portone.sdk.server.schemas.PaymentFilterInput
import io.portone.sdk.server.schemas.PaymentLogistics
import io.portone.sdk.server.schemas.PaymentNotFoundError
import io.portone.sdk.server.schemas.PaymentNotFoundException
import io.portone.sdk.server.schemas.PaymentNotPaidError
import io.portone.sdk.server.schemas.PaymentNotPaidException
import io.portone.sdk.server.schemas.PaymentNotWaitingForDepositError
import io.portone.sdk.server.schemas.PaymentNotWaitingForDepositException
import io.portone.sdk.server.schemas.PaymentProduct
import io.portone.sdk.server.schemas.PaymentProductType
import io.portone.sdk.server.schemas.PaymentSchedule
import io.portone.sdk.server.schemas.PaymentScheduleAlreadyExistsError
import io.portone.sdk.server.schemas.PaymentScheduleAlreadyExistsException
import io.portone.sdk.server.schemas.PaymentScheduleAlreadyProcessedError
import io.portone.sdk.server.schemas.PaymentScheduleAlreadyProcessedException
import io.portone.sdk.server.schemas.PaymentScheduleAlreadyRevokedError
import io.portone.sdk.server.schemas.PaymentScheduleAlreadyRevokedException
import io.portone.sdk.server.schemas.PaymentScheduleFilterInput
import io.portone.sdk.server.schemas.PaymentScheduleNotFoundError
import io.portone.sdk.server.schemas.PaymentScheduleNotFoundException
import io.portone.sdk.server.schemas.PaymentScheduleSortInput
import io.portone.sdk.server.schemas.PgProviderError
import io.portone.sdk.server.schemas.PgProviderException
import io.portone.sdk.server.schemas.PreRegisterPaymentBody
import io.portone.sdk.server.schemas.PreRegisterPaymentError
import io.portone.sdk.server.schemas.PromotionPayMethodDoesNotMatchError
import io.portone.sdk.server.schemas.PromotionPayMethodDoesNotMatchException
import io.portone.sdk.server.schemas.RegisterEscrowLogisticsBody
import io.portone.sdk.server.schemas.RegisterStoreReceiptBody
import io.portone.sdk.server.schemas.RegisterStoreReceiptBodyItem
import io.portone.sdk.server.schemas.RegisterStoreReceiptError
import io.portone.sdk.server.schemas.RegisterStoreReceiptResponse
import io.portone.sdk.server.schemas.RemainedAmountLessThanPromotionMinPaymentAmountError
import io.portone.sdk.server.schemas.RemainedAmountLessThanPromotionMinPaymentAmountException
import io.portone.sdk.server.schemas.ResendIdentityVerificationError
import io.portone.sdk.server.schemas.ResendWebhookBody
import io.portone.sdk.server.schemas.ResendWebhookError
import io.portone.sdk.server.schemas.ResendWebhookResponse
import io.portone.sdk.server.schemas.RevokePaymentSchedulesBody
import io.portone.sdk.server.schemas.RevokePaymentSchedulesError
import io.portone.sdk.server.schemas.RevokePaymentSchedulesResponse
import io.portone.sdk.server.schemas.SendIdentityVerificationBody
import io.portone.sdk.server.schemas.SendIdentityVerificationBodyCustomer
import io.portone.sdk.server.schemas.SendIdentityVerificationError
import io.portone.sdk.server.schemas.SeparatedAddressInput
import io.portone.sdk.server.schemas.SumOfPartsExceedsCancelAmountError
import io.portone.sdk.server.schemas.SumOfPartsExceedsCancelAmountException
import io.portone.sdk.server.schemas.SumOfPartsExceedsTotalAmountError
import io.portone.sdk.server.schemas.SumOfPartsExceedsTotalAmountException
import io.portone.sdk.server.schemas.UnauthorizedError
import io.portone.sdk.server.schemas.UnauthorizedException
import io.portone.sdk.server.schemas.WebhookNotFoundError
import io.portone.sdk.server.schemas.WebhookNotFoundException
import java.io.Closeable
import java.time.Instant
import java.util.concurrent.CompletableFuture
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Unit
import kotlin.collections.List
import kotlin.jvm.JvmName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

public class PortOneApi(
  private val apiSecret: String,
  private val storeId: String? = null,
) : Closeable {
  private val client: HttpClient = HttpClient(OkHttp)

  private val json: Json = Json { ignoreUnknownKeys = true }

  override fun close() {
    client.close()
  }

  /**
   * 본인인증 단건 조회
   *
   * 주어진 아이디에 대응되는 본인인증 내역을 조회합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws IdentityVerificationNotFoundError 요청된 본인인증 건이 존재하지 않는 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param identityVerificationId 조회할 본인인증 아이디
   * @return 성공 응답으로 본인 인증 객체를 반환합니다.
   */
  @JvmName("getIdentityVerificationSuspend")
  public suspend fun getIdentityVerification(identityVerificationId: String): IdentityVerification {
    val httpResponse = client.get("https://api.portone.io/identity-verifications") {
      url {
        appendPathSegments(identityVerificationId)
        if (storeId != null) parameters.append("storeId", storeId)
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<GetIdentityVerificationError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is IdentityVerificationNotFoundError -> throw IdentityVerificationNotFoundException(message
            = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<IdentityVerification>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("getIdentityVerification")
  public fun getIdentityVerificationFuture(identityVerificationId: String):
      CompletableFuture<IdentityVerification> = GlobalScope.future {
      getIdentityVerification(identityVerificationId) }

  /**
   * 본인인증 요청 전송
   *
   * SMS 또는 APP 방식을 이용하여 본인인증 요청을 전송합니다.
   *
   * @throws ChannelNotFoundError 요청된 채널이 존재하지 않는 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws IdentityVerificationAlreadySentError 본인인증 건이 이미 API로 요청된 상태인 경우
   * @throws IdentityVerificationAlreadyVerifiedError 본인인증 건이 이미 인증 완료된 상태인 경우
   * @throws IdentityVerificationNotFoundError 요청된 본인인증 건이 존재하지 않는 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param identityVerificationId 본인인증 아이디
   * @param channelKey 채널 키
   * @param customer 고객 정보
   * @param customData 사용자 지정 데이터
   * @param bypass PG사별 추가 파라미터 ("PG사별 연동 가이드" 참고)
   * @param operator 통신사
   * @param method 본인인증 방식
   */
  @JvmName("sendIdentityVerificationSuspend")
  public suspend fun sendIdentityVerification(
    identityVerificationId: String,
    channelKey: String,
    customer: SendIdentityVerificationBodyCustomer,
    customData: String? = null,
    bypass: JsonObject? = null,
    `operator`: IdentityVerificationOperator,
    method: IdentityVerificationMethod,
  ) {
    val httpResponse = client.post("https://api.portone.io/identity-verifications") {
      url {
        appendPathSegments(identityVerificationId, "send")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(SendIdentityVerificationBody(storeId = storeId,
          channelKey = channelKey, customer = customer, customData = customData, bypass = bypass,
          operator = operator, method = method)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<SendIdentityVerificationError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ChannelNotFoundError -> throw ChannelNotFoundException(message = httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is IdentityVerificationAlreadySentError -> throw
            IdentityVerificationAlreadySentException(message = httpBodyDecoded.message)
        is IdentityVerificationAlreadyVerifiedError -> throw
            IdentityVerificationAlreadyVerifiedException(message = httpBodyDecoded.message)
        is IdentityVerificationNotFoundError -> throw IdentityVerificationNotFoundException(message
            = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
  }

  /**
   * @suppress
   */
  @JvmName("sendIdentityVerification")
  public fun sendIdentityVerificationFuture(
    identityVerificationId: String,
    channelKey: String,
    customer: SendIdentityVerificationBodyCustomer,
    customData: String? = null,
    bypass: JsonObject? = null,
    `operator`: IdentityVerificationOperator,
    method: IdentityVerificationMethod,
  ): CompletableFuture<Unit> = GlobalScope.future { sendIdentityVerification(identityVerificationId,
      channelKey, customer, customData, bypass, operator, method) }

  /**
   * 본인인증 확인
   *
   * 요청된 본인인증에 대한 확인을 진행합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws IdentityVerificationAlreadyVerifiedError 본인인증 건이 이미 인증 완료된 상태인 경우
   * @throws IdentityVerificationNotFoundError 요청된 본인인증 건이 존재하지 않는 경우
   * @throws IdentityVerificationNotSentError 본인인증 건이 API로 요청된 상태가 아닌 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param identityVerificationId 본인인증 아이디
   * @param otp OTP (One-Time Password) - SMS 방식에서만 사용됩니다.
   * @return 성공 응답
   */
  @JvmName("confirmIdentityVerificationSuspend")
  public suspend fun confirmIdentityVerification(identityVerificationId: String, otp: String? =
      null): ConfirmIdentityVerificationResponse {
    val httpResponse = client.post("https://api.portone.io/identity-verifications") {
      url {
        appendPathSegments(identityVerificationId, "confirm")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(ConfirmIdentityVerificationBody(storeId = storeId,
          otp = otp)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<ConfirmIdentityVerificationError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is IdentityVerificationAlreadyVerifiedError -> throw
            IdentityVerificationAlreadyVerifiedException(message = httpBodyDecoded.message)
        is IdentityVerificationNotFoundError -> throw IdentityVerificationNotFoundException(message
            = httpBodyDecoded.message)
        is IdentityVerificationNotSentError -> throw IdentityVerificationNotSentException(message =
            httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<ConfirmIdentityVerificationResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("confirmIdentityVerification")
  public fun confirmIdentityVerificationFuture(identityVerificationId: String, otp: String? = null):
      CompletableFuture<ConfirmIdentityVerificationResponse> = GlobalScope.future {
      confirmIdentityVerification(identityVerificationId, otp) }

  /**
   * SMS 본인인증 요청 재전송
   *
   * SMS 본인인증 요청을 재전송합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws IdentityVerificationAlreadyVerifiedError 본인인증 건이 이미 인증 완료된 상태인 경우
   * @throws IdentityVerificationNotFoundError 요청된 본인인증 건이 존재하지 않는 경우
   * @throws IdentityVerificationNotSentError 본인인증 건이 API로 요청된 상태가 아닌 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param identityVerificationId 본인인증 아이디
   */
  @JvmName("resendIdentityVerificationSuspend")
  public suspend fun resendIdentityVerification(identityVerificationId: String) {
    val httpResponse = client.post("https://api.portone.io/identity-verifications") {
      url {
        appendPathSegments(identityVerificationId, "resend")
        if (storeId != null) parameters.append("storeId", storeId)
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody("{}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<ResendIdentityVerificationError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is IdentityVerificationAlreadyVerifiedError -> throw
            IdentityVerificationAlreadyVerifiedException(message = httpBodyDecoded.message)
        is IdentityVerificationNotFoundError -> throw IdentityVerificationNotFoundException(message
            = httpBodyDecoded.message)
        is IdentityVerificationNotSentError -> throw IdentityVerificationNotSentException(message =
            httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
  }

  /**
   * @suppress
   */
  @JvmName("resendIdentityVerification")
  public fun resendIdentityVerificationFuture(identityVerificationId: String):
      CompletableFuture<Unit> = GlobalScope.future {
      resendIdentityVerification(identityVerificationId) }

  /**
   * 결제 정보 사전 등록
   *
   * 결제 정보를 사전 등록합니다.
   *
   * @throws AlreadyPaidError 결제가 이미 완료된 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @param totalAmount 결제 총 금액
   * @param taxFreeAmount 결제 면세 금액
   * @param currency 통화 단위
   */
  @JvmName("preRegisterPaymentSuspend")
  public suspend fun preRegisterPayment(
    paymentId: String,
    totalAmount: Long? = null,
    taxFreeAmount: Long? = null,
    currency: Currency? = null,
  ) {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "pre-register")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(PreRegisterPaymentBody(storeId = storeId,
          totalAmount = totalAmount, taxFreeAmount = taxFreeAmount, currency = currency)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<PreRegisterPaymentError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is AlreadyPaidError -> throw AlreadyPaidException(message = httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
  }

  /**
   * @suppress
   */
  @JvmName("preRegisterPayment")
  public fun preRegisterPaymentFuture(
    paymentId: String,
    totalAmount: Long? = null,
    taxFreeAmount: Long? = null,
    currency: Currency? = null,
  ): CompletableFuture<Unit> = GlobalScope.future { preRegisterPayment(paymentId, totalAmount,
      taxFreeAmount, currency) }

  /**
   * 빌링키 단건 조회
   *
   * 주어진 빌링키에 대응되는 빌링키 정보를 조회합니다.
   *
   * @throws BillingKeyNotFoundError 빌링키가 존재하지 않는 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param billingKey 조회할 빌링키
   * @return 성공 응답으로 빌링키 정보를 반환합니다.
   */
  @JvmName("getBillingKeyInfoSuspend")
  public suspend fun getBillingKeyInfo(billingKey: String): BillingKeyInfo {
    val httpResponse = client.get("https://api.portone.io/billing-keys") {
      url {
        appendPathSegments(billingKey)
        if (storeId != null) parameters.append("storeId", storeId)
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<GetBillingKeyInfoError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is BillingKeyNotFoundError -> throw BillingKeyNotFoundException(message =
            httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<BillingKeyInfo>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("getBillingKeyInfo")
  public fun getBillingKeyInfoFuture(billingKey: String): CompletableFuture<BillingKeyInfo> =
      GlobalScope.future { getBillingKeyInfo(billingKey) }

  /**
   * 빌링키 삭제
   *
   * 빌링키를 삭제합니다.
   *
   * @throws BillingKeyAlreadyDeletedError 빌링키가 이미 삭제된 경우
   * @throws BillingKeyNotFoundError 빌링키가 존재하지 않는 경우
   * @throws BillingKeyNotIssuedError BillingKeyNotIssuedError
   * @throws ChannelSpecificError 여러 채널을 지정한 요청에서, 채널 각각에서 오류가 발생한 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentScheduleAlreadyExistsError 결제 예약건이 이미 존재하는 경우
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param billingKey 삭제할 빌링키
   * @return 성공 응답
   */
  @JvmName("deleteBillingKeySuspend")
  public suspend fun deleteBillingKey(billingKey: String): DeleteBillingKeyResponse {
    val httpResponse = client.delete("https://api.portone.io/billing-keys") {
      url {
        appendPathSegments(billingKey)
        if (storeId != null) parameters.append("storeId", storeId)
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<DeleteBillingKeyError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is BillingKeyAlreadyDeletedError -> throw BillingKeyAlreadyDeletedException(message =
            httpBodyDecoded.message)
        is BillingKeyNotFoundError -> throw BillingKeyNotFoundException(message =
            httpBodyDecoded.message)
        is BillingKeyNotIssuedError -> throw BillingKeyNotIssuedException(message =
            httpBodyDecoded.message)
        is ChannelSpecificError -> throw ChannelSpecificException(message = httpBodyDecoded.message,
            failures = httpBodyDecoded.failures, succeededChannels =
            httpBodyDecoded.succeededChannels)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentScheduleAlreadyExistsError -> throw PaymentScheduleAlreadyExistsException(message
            = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<DeleteBillingKeyResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("deleteBillingKey")
  public fun deleteBillingKeyFuture(billingKey: String): CompletableFuture<DeleteBillingKeyResponse>
      = GlobalScope.future { deleteBillingKey(billingKey) }

  /**
   * 빌링키 다건 조회
   *
   * 주어진 조건에 맞는 빌링키들을 페이지 기반으로 조회합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param page 요청할 페이지 정보 - 미 입력 시 number: 0, size: 10 으로 기본값이 적용됩니다.
   * @param sort 정렬 조건 - 미 입력 시 sortBy: TIME_TO_PAY, sortOrder: DESC 으로 기본값이 적용됩니다.
   * @param filter 조회할 빌링키 조건 필터 - V1 빌링키 건의 경우 일부 필드에 대해 필터가 적용되지 않을 수 있습니다.
   * @return 성공 응답으로 조회된 빌링키 리스트와 페이지 정보가 반환됩니다.
   */
  @JvmName("getBillingKeyInfosSuspend")
  public suspend fun getBillingKeyInfos(
    page: PageInput? = null,
    sort: BillingKeySortInput? = null,
    filter: BillingKeyFilterInput? = null,
  ): GetBillingKeyInfosResponse {
    val httpResponse = client.get("https://api.portone.io/billing-keys") {
      url {
        parameters.append("requestBody",
            this@PortOneApi.json.encodeToString(GetBillingKeyInfosBody(page = page, sort = sort,
            filter = filter)))
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<GetBillingKeyInfosError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<GetBillingKeyInfosResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("getBillingKeyInfos")
  public fun getBillingKeyInfosFuture(
    page: PageInput? = null,
    sort: BillingKeySortInput? = null,
    filter: BillingKeyFilterInput? = null,
  ): CompletableFuture<GetBillingKeyInfosResponse> = GlobalScope.future { getBillingKeyInfos(page,
      sort, filter) }

  /**
   * 빌링키 발급
   *
   * 빌링키 발급을 요청합니다.
   *
   * @throws ChannelNotFoundError 요청된 채널이 존재하지 않는 경우
   * @throws ChannelSpecificError 여러 채널을 지정한 요청에서, 채널 각각에서 오류가 발생한 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param method 빌링키 결제 수단 정보
   * @param channelKey 채널 키 - 채널 키 또는 채널 그룹 ID 필수
   * @param channelGroupId 채널 그룹 ID - 채널 키 또는 채널 그룹 ID 필수
   * @param customer 고객 정보
   * @param customData 사용자 지정 데이터
   * @param bypass PG사별 추가 파라미터 ("PG사별 연동 가이드" 참고)
   * @param noticeUrls 웹훅 주소 - 빌링키 발급 시 요청을 받을 웹훅 주소입니다.
   * 상점에 설정되어 있는 값보다 우선적으로 적용됩니다.
   * 입력된 값이 없을 경우에는 빈 배열로 해석됩니다.
   * @return 성공 응답
   */
  @JvmName("issueBillingKeySuspend")
  public suspend fun issueBillingKey(
    method: InstantBillingKeyPaymentMethodInput,
    channelKey: String? = null,
    channelGroupId: String? = null,
    customer: CustomerInput? = null,
    customData: String? = null,
    bypass: JsonObject? = null,
    noticeUrls: List<String>? = null,
  ): IssueBillingKeyResponse {
    val httpResponse = client.post("https://api.portone.io/billing-keys") {
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(IssueBillingKeyBody(storeId = storeId, method =
          method, channelKey = channelKey, channelGroupId = channelGroupId, customer = customer,
          customData = customData, bypass = bypass, noticeUrls = noticeUrls)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<IssueBillingKeyError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ChannelNotFoundError -> throw ChannelNotFoundException(message = httpBodyDecoded.message)
        is ChannelSpecificError -> throw ChannelSpecificException(message = httpBodyDecoded.message,
            failures = httpBodyDecoded.failures, succeededChannels =
            httpBodyDecoded.succeededChannels)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<IssueBillingKeyResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("issueBillingKey")
  public fun issueBillingKeyFuture(
    method: InstantBillingKeyPaymentMethodInput,
    channelKey: String? = null,
    channelGroupId: String? = null,
    customer: CustomerInput? = null,
    customData: String? = null,
    bypass: JsonObject? = null,
    noticeUrls: List<String>? = null,
  ): CompletableFuture<IssueBillingKeyResponse> = GlobalScope.future { issueBillingKey(method,
      channelKey, channelGroupId, customer, customData, bypass, noticeUrls) }

  /**
   * 현금 영수증 단건 조회
   *
   * 주어진 결제 아이디에 대응되는 현금 영수증 내역을 조회합니다.
   *
   * @throws CashReceiptNotFoundError 현금영수증이 존재하지 않는 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @return 성공 응답으로 현금 영수증 객체를 반환합니다.
   */
  @JvmName("getCashReceiptByPaymentIdSuspend")
  public suspend fun getCashReceiptByPaymentId(paymentId: String): CashReceipt {
    val httpResponse = client.get("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "cash-receipt")
        if (storeId != null) parameters.append("storeId", storeId)
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<GetCashReceiptError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is CashReceiptNotFoundError -> throw CashReceiptNotFoundException(message =
            httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<CashReceipt>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("getCashReceiptByPaymentId")
  public fun getCashReceiptByPaymentIdFuture(paymentId: String): CompletableFuture<CashReceipt> =
      GlobalScope.future { getCashReceiptByPaymentId(paymentId) }

  /**
   * 결제 단건 조회
   *
   * 주어진 아이디에 대응되는 결제 건을 조회합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentNotFoundError 결제 건이 존재하지 않는 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 조회할 결제 아이디
   * @return 성공 응답으로 결제 건 객체를 반환합니다.
   */
  @JvmName("getPaymentSuspend")
  public suspend fun getPayment(paymentId: String): Payment {
    val httpResponse = client.get("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId)
        if (storeId != null) parameters.append("storeId", storeId)
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<GetPaymentError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentNotFoundError -> throw PaymentNotFoundException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<Payment>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("getPayment")
  public fun getPaymentFuture(paymentId: String): CompletableFuture<Payment> = GlobalScope.future {
      getPayment(paymentId) }

  /**
   * 결제 다건 조회(페이지 기반)
   *
   * 주어진 조건에 맞는 결제 건들을 페이지 기반으로 조회합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param page 요청할 페이지 정보 - 미 입력 시 number: 0, size: 10 으로 기본값이 적용됩니다.
   * @param filter 조회할 결제 건 조건 필터 - V1 결제 건의 경우 일부 필드에 대해 필터가 적용되지 않을 수 있습니다.
   * @return 성공 응답으로 조회된 결제 건 리스트와 페이지 정보가 반환됩니다.
   */
  @JvmName("getPaymentsSuspend")
  public suspend fun getPayments(page: PageInput? = null, filter: PaymentFilterInput? = null):
      GetPaymentsResponse {
    val httpResponse = client.get("https://api.portone.io/payments") {
      url {
        parameters.append("requestBody", this@PortOneApi.json.encodeToString(GetPaymentsBody(page =
            page, filter = filter)))
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<GetPaymentsError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<GetPaymentsResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("getPayments")
  public fun getPaymentsFuture(page: PageInput? = null, filter: PaymentFilterInput? = null):
      CompletableFuture<GetPaymentsResponse> = GlobalScope.future { getPayments(page, filter) }

  /**
   * 결제 대용량 다건 조회(커서 기반)
   *
   * 기간 내 모든 결제 건을 커서 기반으로 조회합니다. 결제 건의 생성일시를 기준으로 주어진 기간 내 존재하는 모든 결제 건이 조회됩니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param from 결제 건 생성시점 범위 조건의 시작 - 값을 입력하지 않으면 end의 90일 전으로 설정됩니다.
   * @param until 결제 건 생성시점 범위 조건의 끝 - 값을 입력하지 않으면 현재 시점으로 설정됩니다.
   * @param cursor 커서 - 결제 건 리스트 중 어디서부터 읽어야 할지 가리키는 값입니다. 최초 요청일 경우 값을 입력하지 마시되, 두번째 요청 부터는 이전 요청
   * 응답값의 cursor를 입력해주시면 됩니다.
   * @param size 페이지 크기 - 미입력 시 기본값은 10 이며 최대 1000까지 허용
   * @return 성공 응답으로 조회된 결제 건 리스트와 커서 정보가 반환됩니다.
   */
  @JvmName("getAllPaymentsByCursorSuspend")
  public suspend fun getAllPaymentsByCursor(
    from: Instant? = null,
    until: Instant? = null,
    cursor: String? = null,
    size: Int? = null,
  ): GetAllPaymentsByCursorResponse {
    val httpResponse = client.get("https://api.portone.io/payments-by-cursor") {
      url {
        parameters.append("requestBody",
            this@PortOneApi.json.encodeToString(GetAllPaymentsByCursorBody(storeId = storeId, from =
            from, until = until, cursor = cursor, size = size)))
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<GetAllPaymentsError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<GetAllPaymentsByCursorResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("getAllPaymentsByCursor")
  public fun getAllPaymentsByCursorFuture(
    from: Instant? = null,
    until: Instant? = null,
    cursor: String? = null,
    size: Int? = null,
  ): CompletableFuture<GetAllPaymentsByCursorResponse> = GlobalScope.future {
      getAllPaymentsByCursor(from, until, cursor, size) }

  /**
   * 결제 예약 단건 조회
   *
   * 주어진 아이디에 대응되는 결제 예약 건을 조회합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentScheduleNotFoundError 결제 예약건이 존재하지 않는 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentScheduleId 조회할 결제 예약 건 아이디
   * @return 성공 응답으로 결제 예약 건 객체를 반환합니다.
   */
  @JvmName("getPaymentScheduleSuspend")
  public suspend fun getPaymentSchedule(paymentScheduleId: String): PaymentSchedule {
    val httpResponse = client.get("https://api.portone.io/payment-schedules") {
      url {
        appendPathSegments(paymentScheduleId)
        if (storeId != null) parameters.append("storeId", storeId)
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<GetPaymentScheduleError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentScheduleNotFoundError -> throw PaymentScheduleNotFoundException(message =
            httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<PaymentSchedule>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("getPaymentSchedule")
  public fun getPaymentScheduleFuture(paymentScheduleId: String): CompletableFuture<PaymentSchedule>
      = GlobalScope.future { getPaymentSchedule(paymentScheduleId) }

  /**
   * 결제 예약 다건 조회
   *
   * 주어진 조건에 맞는 결제 예약 건들을 조회합니다.
   * `filter.from`, `filter.until` 파라미터의 기본값이 결제 시점 기준 지난 90일에 속하는 건을 조회하도록 되어 있으니, 미래 예약 상태의 건을
   * 조회하기 위해서는 해당 파라미터를 직접 설정해 주셔야 합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param page 요청할 페이지 정보 - 미 입력 시 number: 0, size: 10 으로 기본값이 적용됩니다.
   * @param sort 정렬 조건 - 미 입력 시 sortBy: TIME_TO_PAY, sortOrder: DESC 으로 기본값이 적용됩니다.
   * @param filter 조회할 결제 예약 건의 조건 필터
   * @return 성공 응답으로 조회된 예약 결제 건 리스트가 반환됩니다.
   */
  @JvmName("getPaymentSchedulesSuspend")
  public suspend fun getPaymentSchedules(
    page: PageInput? = null,
    sort: PaymentScheduleSortInput? = null,
    filter: PaymentScheduleFilterInput? = null,
  ): GetPaymentSchedulesResponse {
    val httpResponse = client.get("https://api.portone.io/payment-schedules") {
      url {
        parameters.append("requestBody",
            this@PortOneApi.json.encodeToString(GetPaymentSchedulesBody(page = page, sort = sort,
            filter = filter)))
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<GetPaymentSchedulesError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<GetPaymentSchedulesResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("getPaymentSchedules")
  public fun getPaymentSchedulesFuture(
    page: PageInput? = null,
    sort: PaymentScheduleSortInput? = null,
    filter: PaymentScheduleFilterInput? = null,
  ): CompletableFuture<GetPaymentSchedulesResponse> = GlobalScope.future { getPaymentSchedules(page,
      sort, filter) }

  /**
   * 결제 예약 취소
   *
   * 결제 예약 건을 취소합니다.
   *
   * @throws BillingKeyAlreadyDeletedError 빌링키가 이미 삭제된 경우
   * @throws BillingKeyNotFoundError 빌링키가 존재하지 않는 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentScheduleAlreadyProcessedError 결제 예약건이 이미 처리된 경우
   * @throws PaymentScheduleAlreadyRevokedError 결제 예약건이 이미 취소된 경우
   * @throws PaymentScheduleNotFoundError 결제 예약건이 존재하지 않는 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param billingKey 빌링키
   * @param scheduleIds 결제 예약 건 아이디 목록
   * @return 성공 응답
   */
  @JvmName("revokePaymentSchedulesSuspend")
  public suspend fun revokePaymentSchedules(billingKey: String? = null, scheduleIds: List<String>? =
      null): RevokePaymentSchedulesResponse {
    val httpResponse = client.delete("https://api.portone.io/payment-schedules") {
      url {
        parameters.append("requestBody",
            this@PortOneApi.json.encodeToString(RevokePaymentSchedulesBody(storeId = storeId,
            billingKey = billingKey, scheduleIds = scheduleIds)))
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<RevokePaymentSchedulesError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is BillingKeyAlreadyDeletedError -> throw BillingKeyAlreadyDeletedException(message =
            httpBodyDecoded.message)
        is BillingKeyNotFoundError -> throw BillingKeyNotFoundException(message =
            httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentScheduleAlreadyProcessedError -> throw
            PaymentScheduleAlreadyProcessedException(message = httpBodyDecoded.message)
        is PaymentScheduleAlreadyRevokedError -> throw
            PaymentScheduleAlreadyRevokedException(message = httpBodyDecoded.message)
        is PaymentScheduleNotFoundError -> throw PaymentScheduleNotFoundException(message =
            httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<RevokePaymentSchedulesResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("revokePaymentSchedules")
  public fun revokePaymentSchedulesFuture(billingKey: String? = null, scheduleIds: List<String>? =
      null): CompletableFuture<RevokePaymentSchedulesResponse> = GlobalScope.future {
      revokePaymentSchedules(billingKey, scheduleIds) }

  /**
   * 결제 예약
   *
   * 결제를 예약합니다.
   *
   * @throws AlreadyPaidOrWaitingError 결제가 이미 완료되었거나 대기중인 경우
   * @throws BillingKeyAlreadyDeletedError 빌링키가 이미 삭제된 경우
   * @throws BillingKeyNotFoundError 빌링키가 존재하지 않는 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentScheduleAlreadyExistsError 결제 예약건이 이미 존재하는 경우
   * @throws SumOfPartsExceedsTotalAmountError 면세 금액 등 하위 항목들의 합이 전체 결제 금액을 초과한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @param payment 빌링키 결제 입력 정보
   * @param timeToPay 결제 예정 시점
   * @return 성공 응답
   */
  @JvmName("createPaymentScheduleSuspend")
  public suspend fun createPaymentSchedule(
    paymentId: String,
    payment: BillingKeyPaymentInput,
    timeToPay: Instant,
  ): CreatePaymentScheduleResponse {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "schedule")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(CreatePaymentScheduleBody(payment = payment,
          timeToPay = timeToPay)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<CreatePaymentScheduleError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is AlreadyPaidOrWaitingError -> throw AlreadyPaidOrWaitingException(message =
            httpBodyDecoded.message)
        is BillingKeyAlreadyDeletedError -> throw BillingKeyAlreadyDeletedException(message =
            httpBodyDecoded.message)
        is BillingKeyNotFoundError -> throw BillingKeyNotFoundException(message =
            httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentScheduleAlreadyExistsError -> throw PaymentScheduleAlreadyExistsException(message
            = httpBodyDecoded.message)
        is SumOfPartsExceedsTotalAmountError -> throw SumOfPartsExceedsTotalAmountException(message
            = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<CreatePaymentScheduleResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("createPaymentSchedule")
  public fun createPaymentScheduleFuture(
    paymentId: String,
    payment: BillingKeyPaymentInput,
    timeToPay: Instant,
  ): CompletableFuture<CreatePaymentScheduleResponse> = GlobalScope.future {
      createPaymentSchedule(paymentId, payment, timeToPay) }

  /**
   * 결제 취소
   *
   * 결제 취소를 요청합니다.
   *
   * @throws CancellableAmountConsistencyBrokenError 취소 가능 잔액 검증에 실패한 경우
   * @throws CancelAmountExceedsCancellableAmountError 결제 취소 금액이 취소 가능 금액을 초과한 경우
   * @throws CancelTaxAmountExceedsCancellableTaxAmountError 취소 과세 금액이 취소 가능한 과세 금액을 초과한 경우
   * @throws CancelTaxFreeAmountExceedsCancellableTaxFreeAmountError 취소 면세 금액이 취소 가능한 면세 금액을 초과한 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentAlreadyCancelledError 결제가 이미 취소된 경우
   * @throws PaymentNotFoundError 결제 건이 존재하지 않는 경우
   * @throws PaymentNotPaidError 결제가 완료되지 않은 경우
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws RemainedAmountLessThanPromotionMinPaymentAmountError 부분 취소 시, 취소하게 될 경우 남은 금액이 프로모션의 최소
   * 결제 금액보다 작아지는 경우
   * @throws SumOfPartsExceedsCancelAmountError 면세 금액 등 하위 항목들의 합이 전체 취소 금액을 초과한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @param amount 취소 총 금액 - 값을 입력하지 않으면 전액 취소됩니다.
   * @param taxFreeAmount 취소 금액 중 면세 금액 - 값을 입력하지 않으면 전액 과세 취소됩니다.
   * @param vatAmount 취소 금액 중 부가세액 - 값을 입력하지 않으면 자동 계산됩니다.
   * @param reason 취소 사유
   * @param requester 취소 요청자 - 고객에 의한 취소일 경우 Customer, 관리자에 의한 취소일 경우 Admin으로 입력합니다.
   * @param currentCancellableAmount 결제 건의 취소 가능 잔액 - 본 취소 요청 이전의 취소 가능 잔액으로써, 값을 입력하면 잔액이 일치하는 경우에만
   * 취소가 진행됩니다. 값을 입력하지 않으면 별도의 검증 처리를 수행하지 않습니다.
   * @param refundAccount 환불 계좌 - 계좌 환불일 경우 입력합니다. 계좌 환불이 필요한 경우는 가상계좌 환불, 휴대폰 익월 환불 등이 있습니다.
   * @return 성공 응답
   */
  @JvmName("cancelPaymentSuspend")
  public suspend fun cancelPayment(
    paymentId: String,
    amount: Long? = null,
    taxFreeAmount: Long? = null,
    vatAmount: Long? = null,
    reason: String,
    requester: CancelRequester? = null,
    currentCancellableAmount: Long? = null,
    refundAccount: CancelPaymentBodyRefundAccount? = null,
  ): CancelPaymentResponse {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "cancel")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(CancelPaymentBody(storeId = storeId, amount =
          amount, taxFreeAmount = taxFreeAmount, vatAmount = vatAmount, reason = reason, requester =
          requester, currentCancellableAmount = currentCancellableAmount, refundAccount =
          refundAccount)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<CancelPaymentError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is CancellableAmountConsistencyBrokenError -> throw
            CancellableAmountConsistencyBrokenException(message = httpBodyDecoded.message)
        is CancelAmountExceedsCancellableAmountError -> throw
            CancelAmountExceedsCancellableAmountException(message = httpBodyDecoded.message)
        is CancelTaxAmountExceedsCancellableTaxAmountError -> throw
            CancelTaxAmountExceedsCancellableTaxAmountException(message = httpBodyDecoded.message)
        is CancelTaxFreeAmountExceedsCancellableTaxFreeAmountError -> throw
            CancelTaxFreeAmountExceedsCancellableTaxFreeAmountException(message =
            httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentAlreadyCancelledError -> throw PaymentAlreadyCancelledException(message =
            httpBodyDecoded.message)
        is PaymentNotFoundError -> throw PaymentNotFoundException(message = httpBodyDecoded.message)
        is PaymentNotPaidError -> throw PaymentNotPaidException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is RemainedAmountLessThanPromotionMinPaymentAmountError -> throw
            RemainedAmountLessThanPromotionMinPaymentAmountException(message =
            httpBodyDecoded.message)
        is SumOfPartsExceedsCancelAmountError -> throw
            SumOfPartsExceedsCancelAmountException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<CancelPaymentResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("cancelPayment")
  public fun cancelPaymentFuture(
    paymentId: String,
    amount: Long? = null,
    taxFreeAmount: Long? = null,
    vatAmount: Long? = null,
    reason: String,
    requester: CancelRequester? = null,
    currentCancellableAmount: Long? = null,
    refundAccount: CancelPaymentBodyRefundAccount? = null,
  ): CompletableFuture<CancelPaymentResponse> = GlobalScope.future { cancelPayment(paymentId,
      amount, taxFreeAmount, vatAmount, reason, requester, currentCancellableAmount, refundAccount)
      }

  /**
   * 빌링키 결제
   *
   * 빌링키로 결제를 진행합니다.
   *
   * @throws AlreadyPaidError 결제가 이미 완료된 경우
   * @throws BillingKeyAlreadyDeletedError 빌링키가 이미 삭제된 경우
   * @throws BillingKeyNotFoundError 빌링키가 존재하지 않는 경우
   * @throws ChannelNotFoundError 요청된 채널이 존재하지 않는 경우
   * @throws DiscountAmountExceedsTotalAmountError 프로모션 할인 금액이 결제 시도 금액 이상인 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws PromotionPayMethodDoesNotMatchError 결제수단이 프로모션에 지정된 것과 일치하지 않는 경우
   * @throws SumOfPartsExceedsTotalAmountError 면세 금액 등 하위 항목들의 합이 전체 결제 금액을 초과한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @param billingKey 빌링키 결제에 사용할 빌링키
   * @param channelKey 채널 키 - 다수 채널에 대해 발급된 빌링키에 대해, 결제 채널을 특정하고 싶을 때 명시
   * @param orderName 주문명
   * @param customer 고객 정보
   * @param customData 사용자 지정 데이터
   * @param amount 결제 금액 세부 입력 정보
   * @param currency 통화
   * @param installmentMonth 할부 개월 수
   * @param useFreeInterestFromMerchant 무이자 할부 이자를 고객사가 부담할지 여부
   * @param useCardPoint 카드 포인트 사용 여부
   * @param cashReceipt 현금영수증 정보
   * @param country 결제 국가
   * @param noticeUrls 웹훅 주소 - 결제 승인/실패 시 요청을 받을 웹훅 주소입니다.
   * 상점에 설정되어 있는 값보다 우선적으로 적용됩니다.
   * 입력된 값이 없을 경우에는 빈 배열로 해석됩니다.
   * @param products 상품 정보 - 입력된 값이 없을 경우에는 빈 배열로 해석됩니다.
   * @param productCount 상품 개수
   * @param productType 상품 유형
   * @param shippingAddress 배송지 주소
   * @param promotionId 해당 결제에 적용할 프로모션 아이디
   * @param bypass PG사별 추가 파라미터 ("PG사별 연동 가이드" 참고)
   * @return 성공 응답
   */
  @JvmName("payWithBillingKeySuspend")
  public suspend fun payWithBillingKey(
    paymentId: String,
    billingKey: String,
    channelKey: String? = null,
    orderName: String,
    customer: CustomerInput? = null,
    customData: String? = null,
    amount: PaymentAmountInput,
    currency: Currency,
    installmentMonth: Int? = null,
    useFreeInterestFromMerchant: Boolean? = null,
    useCardPoint: Boolean? = null,
    cashReceipt: CashReceiptInput? = null,
    country: Country? = null,
    noticeUrls: List<String>? = null,
    products: List<PaymentProduct>? = null,
    productCount: Int? = null,
    productType: PaymentProductType? = null,
    shippingAddress: SeparatedAddressInput? = null,
    promotionId: String? = null,
    bypass: JsonObject? = null,
  ): PayWithBillingKeyResponse {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "billing-key")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(BillingKeyPaymentInput(storeId = storeId,
          billingKey = billingKey, channelKey = channelKey, orderName = orderName, customer =
          customer, customData = customData, amount = amount, currency = currency, installmentMonth
          = installmentMonth, useFreeInterestFromMerchant = useFreeInterestFromMerchant,
          useCardPoint = useCardPoint, cashReceipt = cashReceipt, country = country, noticeUrls =
          noticeUrls, products = products, productCount = productCount, productType = productType,
          shippingAddress = shippingAddress, promotionId = promotionId, bypass = bypass)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<PayWithBillingKeyError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is AlreadyPaidError -> throw AlreadyPaidException(message = httpBodyDecoded.message)
        is BillingKeyAlreadyDeletedError -> throw BillingKeyAlreadyDeletedException(message =
            httpBodyDecoded.message)
        is BillingKeyNotFoundError -> throw BillingKeyNotFoundException(message =
            httpBodyDecoded.message)
        is ChannelNotFoundError -> throw ChannelNotFoundException(message = httpBodyDecoded.message)
        is DiscountAmountExceedsTotalAmountError -> throw
            DiscountAmountExceedsTotalAmountException(message = httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is PromotionPayMethodDoesNotMatchError -> throw
            PromotionPayMethodDoesNotMatchException(message = httpBodyDecoded.message)
        is SumOfPartsExceedsTotalAmountError -> throw SumOfPartsExceedsTotalAmountException(message
            = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<PayWithBillingKeyResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("payWithBillingKey")
  public fun payWithBillingKeyFuture(
    paymentId: String,
    billingKey: String,
    channelKey: String? = null,
    orderName: String,
    customer: CustomerInput? = null,
    customData: String? = null,
    amount: PaymentAmountInput,
    currency: Currency,
    installmentMonth: Int? = null,
    useFreeInterestFromMerchant: Boolean? = null,
    useCardPoint: Boolean? = null,
    cashReceipt: CashReceiptInput? = null,
    country: Country? = null,
    noticeUrls: List<String>? = null,
    products: List<PaymentProduct>? = null,
    productCount: Int? = null,
    productType: PaymentProductType? = null,
    shippingAddress: SeparatedAddressInput? = null,
    promotionId: String? = null,
    bypass: JsonObject? = null,
  ): CompletableFuture<PayWithBillingKeyResponse> = GlobalScope.future {
      payWithBillingKey(paymentId, billingKey, channelKey, orderName, customer, customData, amount,
      currency, installmentMonth, useFreeInterestFromMerchant, useCardPoint, cashReceipt, country,
      noticeUrls, products, productCount, productType, shippingAddress, promotionId, bypass) }

  /**
   * 수기 결제
   *
   * 수기 결제를 진행합니다.
   *
   * @throws AlreadyPaidError 결제가 이미 완료된 경우
   * @throws ChannelNotFoundError 요청된 채널이 존재하지 않는 경우
   * @throws DiscountAmountExceedsTotalAmountError 프로모션 할인 금액이 결제 시도 금액 이상인 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws PromotionPayMethodDoesNotMatchError 결제수단이 프로모션에 지정된 것과 일치하지 않는 경우
   * @throws SumOfPartsExceedsTotalAmountError 면세 금액 등 하위 항목들의 합이 전체 결제 금액을 초과한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @param channelKey 채널 키 - 채널 키 또는 채널 그룹 ID 필수
   * @param channelGroupId 채널 그룹 ID - 채널 키 또는 채널 그룹 ID 필수
   * @param method 결제수단 정보
   * @param orderName 주문명
   * @param isCulturalExpense 문화비 지출 여부 - 기본값은 false 입니다.
   * @param isEscrow 에스크로 결제 여부 - 기본값은 false 입니다.
   * @param customer 고객 정보
   * @param customData 사용자 지정 데이터
   * @param amount 결제 금액 세부 입력 정보
   * @param currency 통화
   * @param country 결제 국가
   * @param noticeUrls 웹훅 주소 - 결제 승인/실패 시 요청을 받을 웹훅 주소입니다.
   * 상점에 설정되어 있는 값보다 우선적으로 적용됩니다.
   * 입력된 값이 없을 경우에는 빈 배열로 해석됩니다.
   * @param products 상품 정보 - 입력된 값이 없을 경우에는 빈 배열로 해석됩니다.
   * @param productCount 상품 개수
   * @param productType 상품 유형
   * @param shippingAddress 배송지 주소
   * @param promotionId 해당 결제에 적용할 프로모션 아이디
   * @return 성공 응답
   */
  @JvmName("payInstantlySuspend")
  public suspend fun payInstantly(
    paymentId: String,
    channelKey: String? = null,
    channelGroupId: String? = null,
    method: InstantPaymentMethodInput,
    orderName: String,
    isCulturalExpense: Boolean? = null,
    isEscrow: Boolean? = null,
    customer: CustomerInput? = null,
    customData: String? = null,
    amount: PaymentAmountInput,
    currency: Currency,
    country: Country? = null,
    noticeUrls: List<String>? = null,
    products: List<PaymentProduct>? = null,
    productCount: Int? = null,
    productType: PaymentProductType? = null,
    shippingAddress: SeparatedAddressInput? = null,
    promotionId: String? = null,
  ): PayInstantlyResponse {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "instant")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(InstantPaymentInput(storeId = storeId, channelKey
          = channelKey, channelGroupId = channelGroupId, method = method, orderName = orderName,
          isCulturalExpense = isCulturalExpense, isEscrow = isEscrow, customer = customer,
          customData = customData, amount = amount, currency = currency, country = country,
          noticeUrls = noticeUrls, products = products, productCount = productCount, productType =
          productType, shippingAddress = shippingAddress, promotionId = promotionId)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<PayInstantlyError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is AlreadyPaidError -> throw AlreadyPaidException(message = httpBodyDecoded.message)
        is ChannelNotFoundError -> throw ChannelNotFoundException(message = httpBodyDecoded.message)
        is DiscountAmountExceedsTotalAmountError -> throw
            DiscountAmountExceedsTotalAmountException(message = httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is PromotionPayMethodDoesNotMatchError -> throw
            PromotionPayMethodDoesNotMatchException(message = httpBodyDecoded.message)
        is SumOfPartsExceedsTotalAmountError -> throw SumOfPartsExceedsTotalAmountException(message
            = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<PayInstantlyResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("payInstantly")
  public fun payInstantlyFuture(
    paymentId: String,
    channelKey: String? = null,
    channelGroupId: String? = null,
    method: InstantPaymentMethodInput,
    orderName: String,
    isCulturalExpense: Boolean? = null,
    isEscrow: Boolean? = null,
    customer: CustomerInput? = null,
    customData: String? = null,
    amount: PaymentAmountInput,
    currency: Currency,
    country: Country? = null,
    noticeUrls: List<String>? = null,
    products: List<PaymentProduct>? = null,
    productCount: Int? = null,
    productType: PaymentProductType? = null,
    shippingAddress: SeparatedAddressInput? = null,
    promotionId: String? = null,
  ): CompletableFuture<PayInstantlyResponse> = GlobalScope.future { payInstantly(paymentId,
      channelKey, channelGroupId, method, orderName, isCulturalExpense, isEscrow, customer,
      customData, amount, currency, country, noticeUrls, products, productCount, productType,
      shippingAddress, promotionId) }

  /**
   * 현금 영수증 수동 발급
   *
   * 현금 영수증 발급을 요청합니다.
   *
   * @throws CashReceiptAlreadyIssuedError 현금영수증이 이미 발급된 경우
   * @throws ChannelNotFoundError 요청된 채널이 존재하지 않는 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디 - 외부 결제 건에 대한 수동 발급의 경우, 아이디를 직접 채번하여 입력합니다.
   * @param channelKey 채널 키
   * @param type 현금 영수증 유형
   * @param orderName 주문명
   * @param currency 화폐
   * @param amount 금액 세부 입력 정보
   * @param productType 상품 유형
   * @param customer 고객 정보
   * @param paidAt 결제 일자
   * @return 성공 응답
   */
  @JvmName("issueCashReceiptSuspend")
  public suspend fun issueCashReceipt(
    paymentId: String,
    channelKey: String,
    type: CashReceiptType,
    orderName: String,
    currency: Currency,
    amount: PaymentAmountInput,
    productType: PaymentProductType? = null,
    customer: IssueCashReceiptCustomerInput,
    paidAt: Instant? = null,
  ): IssueCashReceiptResponse {
    val httpResponse = client.post("https://api.portone.io/cash-receipts") {
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(IssueCashReceiptBody(storeId = storeId, paymentId
          = paymentId, channelKey = channelKey, type = type, orderName = orderName, currency =
          currency, amount = amount, productType = productType, customer = customer, paidAt =
          paidAt)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<IssueCashReceiptError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is CashReceiptAlreadyIssuedError -> throw CashReceiptAlreadyIssuedException(message =
            httpBodyDecoded.message)
        is ChannelNotFoundError -> throw ChannelNotFoundException(message = httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<IssueCashReceiptResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("issueCashReceipt")
  public fun issueCashReceiptFuture(
    paymentId: String,
    channelKey: String,
    type: CashReceiptType,
    orderName: String,
    currency: Currency,
    amount: PaymentAmountInput,
    productType: PaymentProductType? = null,
    customer: IssueCashReceiptCustomerInput,
    paidAt: Instant? = null,
  ): CompletableFuture<IssueCashReceiptResponse> = GlobalScope.future { issueCashReceipt(paymentId,
      channelKey, type, orderName, currency, amount, productType, customer, paidAt) }

  /**
   * 현금 영수증 취소
   *
   * 현금 영수증 취소를 요청합니다.
   *
   * @throws CashReceiptNotFoundError 현금영수증이 존재하지 않는 경우
   * @throws CashReceiptNotIssuedError 현금영수증이 발급되지 않은 경우
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @return 성공 응답
   */
  @JvmName("cancelCashReceiptByPaymentIdSuspend")
  public suspend fun cancelCashReceiptByPaymentId(paymentId: String): CancelCashReceiptResponse {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "cash-receipt", "cancel")
        if (storeId != null) parameters.append("storeId", storeId)
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody("{}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<CancelCashReceiptError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is CashReceiptNotFoundError -> throw CashReceiptNotFoundException(message =
            httpBodyDecoded.message)
        is CashReceiptNotIssuedError -> throw CashReceiptNotIssuedException(message =
            httpBodyDecoded.message)
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<CancelCashReceiptResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("cancelCashReceiptByPaymentId")
  public fun cancelCashReceiptByPaymentIdFuture(paymentId: String):
      CompletableFuture<CancelCashReceiptResponse> = GlobalScope.future {
      cancelCashReceiptByPaymentId(paymentId) }

  /**
   * 가상계좌 말소
   *
   * 발급된 가상계좌를 말소합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentNotFoundError 결제 건이 존재하지 않는 경우
   * @throws PaymentNotWaitingForDepositError 결제 건이 입금 대기 상태가 아닌 경우
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @return 성공 응답
   */
  @JvmName("closeVirtualAccountSuspend")
  public suspend fun closeVirtualAccount(paymentId: String): CloseVirtualAccountResponse {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "virtual-account", "close")
        if (storeId != null) parameters.append("storeId", storeId)
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody("{}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<CloseVirtualAccountError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentNotFoundError -> throw PaymentNotFoundException(message = httpBodyDecoded.message)
        is PaymentNotWaitingForDepositError -> throw PaymentNotWaitingForDepositException(message =
            httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<CloseVirtualAccountResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("closeVirtualAccount")
  public fun closeVirtualAccountFuture(paymentId: String):
      CompletableFuture<CloseVirtualAccountResponse> = GlobalScope.future {
      closeVirtualAccount(paymentId) }

  /**
   * 에스크로 배송 정보 등록
   *
   * 에스크로 배송 정보를 등록합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentNotFoundError 결제 건이 존재하지 않는 경우
   * @throws PaymentNotPaidError 결제가 완료되지 않은 경우
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @param sender 에스크로 발송자 정보
   * @param receiver 에스크로 수취인 정보
   * @param logistics 에스크로 물류 정보
   * @param sendEmail 이메일 알림 전송 여부 - 에스크로 구매 확정 시 이메일로 알림을 보낼지 여부입니다.
   * @param products 상품 정보
   * @return 성공 응답
   */
  @JvmName("applyEscrowLogisticsSuspend")
  public suspend fun applyEscrowLogistics(
    paymentId: String,
    sender: PaymentEscrowSenderInput? = null,
    `receiver`: PaymentEscrowReceiverInput? = null,
    logistics: PaymentLogistics,
    sendEmail: Boolean? = null,
    products: List<PaymentProduct>? = null,
  ): ApplyEscrowLogisticsResponse {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "escrow", "logistics")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(RegisterEscrowLogisticsBody(storeId = storeId,
          sender = sender, receiver = receiver, logistics = logistics, sendEmail = sendEmail,
          products = products)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<ApplyEscrowLogisticsError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentNotFoundError -> throw PaymentNotFoundException(message = httpBodyDecoded.message)
        is PaymentNotPaidError -> throw PaymentNotPaidException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<ApplyEscrowLogisticsResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("applyEscrowLogistics")
  public fun applyEscrowLogisticsFuture(
    paymentId: String,
    sender: PaymentEscrowSenderInput? = null,
    `receiver`: PaymentEscrowReceiverInput? = null,
    logistics: PaymentLogistics,
    sendEmail: Boolean? = null,
    products: List<PaymentProduct>? = null,
  ): CompletableFuture<ApplyEscrowLogisticsResponse> = GlobalScope.future {
      applyEscrowLogistics(paymentId, sender, receiver, logistics, sendEmail, products) }

  /**
   * 에스크로 배송 정보 수정
   *
   * 에스크로 배송 정보를 수정합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentNotFoundError 결제 건이 존재하지 않는 경우
   * @throws PaymentNotPaidError 결제가 완료되지 않은 경우
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @param sender 에스크로 발송자 정보
   * @param receiver 에스크로 수취인 정보
   * @param logistics 에스크로 물류 정보
   * @param sendEmail 이메일 알림 전송 여부 - 에스크로 구매 확정 시 이메일로 알림을 보낼지 여부입니다.
   * @param products 상품 정보
   * @return 성공 응답
   */
  @JvmName("modifyEscrowLogisticsSuspend")
  public suspend fun modifyEscrowLogistics(
    paymentId: String,
    sender: PaymentEscrowSenderInput? = null,
    `receiver`: PaymentEscrowReceiverInput? = null,
    logistics: PaymentLogistics,
    sendEmail: Boolean? = null,
    products: List<PaymentProduct>? = null,
  ): ModifyEscrowLogisticsResponse {
    val httpResponse = client.patch("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "escrow", "logistics")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(ModifyEscrowLogisticsBody(storeId = storeId,
          sender = sender, receiver = receiver, logistics = logistics, sendEmail = sendEmail,
          products = products)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<ModifyEscrowLogisticsError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentNotFoundError -> throw PaymentNotFoundException(message = httpBodyDecoded.message)
        is PaymentNotPaidError -> throw PaymentNotPaidException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<ModifyEscrowLogisticsResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("modifyEscrowLogistics")
  public fun modifyEscrowLogisticsFuture(
    paymentId: String,
    sender: PaymentEscrowSenderInput? = null,
    `receiver`: PaymentEscrowReceiverInput? = null,
    logistics: PaymentLogistics,
    sendEmail: Boolean? = null,
    products: List<PaymentProduct>? = null,
  ): CompletableFuture<ModifyEscrowLogisticsResponse> = GlobalScope.future {
      modifyEscrowLogistics(paymentId, sender, receiver, logistics, sendEmail, products) }

  /**
   * 에스크로 구매 확정
   *
   * 에스크로 결제를 구매 확정 처리합니다
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentNotFoundError 결제 건이 존재하지 않는 경우
   * @throws PaymentNotPaidError 결제가 완료되지 않은 경우
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @param fromStore 확인 주체가 상점인지 여부 - 구매확정요청 주체가 고객사 관리자인지 구매자인지 구분하기 위한 필드입니다.
   * 네이버페이 전용 파라미터이며, 구분이 모호한 경우 고객사 관리자(true)로 입력합니다.
   * @return 성공 응답
   */
  @JvmName("confirmEscrowSuspend")
  public suspend fun confirmEscrow(paymentId: String, fromStore: Boolean? = null):
      ConfirmEscrowResponse {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "escrow", "complete")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(ConfirmEscrowBody(storeId = storeId, fromStore =
          fromStore)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<ConfirmEscrowError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentNotFoundError -> throw PaymentNotFoundException(message = httpBodyDecoded.message)
        is PaymentNotPaidError -> throw PaymentNotPaidException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<ConfirmEscrowResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("confirmEscrow")
  public fun confirmEscrowFuture(paymentId: String, fromStore: Boolean? = null):
      CompletableFuture<ConfirmEscrowResponse> = GlobalScope.future { confirmEscrow(paymentId,
      fromStore) }

  /**
   * 웹훅 재발송
   *
   * 웹훅을 재발송합니다.
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentNotFoundError 결제 건이 존재하지 않는 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws WebhookNotFoundError 웹훅 내역이 존재하지 않는 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 결제 건 아이디
   * @param webhookId 웹훅 아이디 - 입력하지 않으면 결제 건의 가장 최근 웹훅 아이디가 기본 적용됩니다
   * @return 성공 응답
   */
  @JvmName("resendWebhookSuspend")
  public suspend fun resendWebhook(paymentId: String, webhookId: String? = null):
      ResendWebhookResponse {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "resend-webhook")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(ResendWebhookBody(storeId = storeId, webhookId =
          webhookId)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<ResendWebhookError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentNotFoundError -> throw PaymentNotFoundException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
        is WebhookNotFoundError -> throw WebhookNotFoundException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<ResendWebhookResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("resendWebhook")
  public fun resendWebhookFuture(paymentId: String, webhookId: String? = null):
      CompletableFuture<ResendWebhookResponse> = GlobalScope.future { resendWebhook(paymentId,
      webhookId) }

  /**
   * 카카오페이 주문 조회 API
   *
   * 주어진 아이디에 대응되는 카카오페이 주문 건을 조회합니다.
   * 해당 API 사용이 필요한 경우 포트원 기술지원팀으로 문의 주시길 바랍니다.
   *
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param pgTxId 카카오페이 주문 번호 (tid)
   * @param channelKey 채널 키
   * @return 성공 응답으로 카카오페이 주문 조회 응답 객체를 반환합니다.
   */
  @JvmName("getKakaopayPaymentOrderSuspend")
  public suspend fun getKakaopayPaymentOrder(pgTxId: String, channelKey: String):
      GetKakaopayPaymentOrderResponse {
    val httpResponse = client.get("https://api.portone.io/kakaopay/payment/order") {
      url {
        parameters.append("pgTxId", pgTxId)
            parameters.append("channelKey", channelKey)
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<GetKakaopayPaymentOrderError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<GetKakaopayPaymentOrderResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("getKakaopayPaymentOrder")
  public fun getKakaopayPaymentOrderFuture(pgTxId: String, channelKey: String):
      CompletableFuture<GetKakaopayPaymentOrderResponse> = GlobalScope.future {
      getKakaopayPaymentOrder(pgTxId, channelKey) }

  /**
   * 영수증 내 하위 상점 거래 등록
   *
   * 결제 내역 매출전표에 하위 상점의 거래를 등록합니다.
   * 지원되는 PG사:
   * KG이니시스(이용 전 콘솔 -> 결제연동 탭에서 INIApi Key 등록 필요)
   *
   * @throws ForbiddenError 요청이 거절된 경우
   * @throws InvalidRequestError 요청된 입력 정보가 유효하지 않은 경우 - 허가되지 않은 값, 올바르지 않은 형식의 요청 등이 모두 해당됩니다.
   * @throws PaymentNotFoundError 결제 건이 존재하지 않는 경우
   * @throws PaymentNotPaidError 결제가 완료되지 않은 경우
   * @throws PgProviderError PG사에서 오류를 전달한 경우
   * @throws UnauthorizedError 인증 정보가 올바르지 않은 경우
   * @throws UnrecognizedException API 응답이 알 수 없는 형식인 경우
   *
   * @param paymentId 등록할 하위 상점 결제 건 아이디
   * @param items 하위 상점 거래 목록
   * @return 성공 응답
   */
  @JvmName("registerStoreReceiptSuspend")
  public suspend fun registerStoreReceipt(paymentId: String,
      items: List<RegisterStoreReceiptBodyItem>): RegisterStoreReceiptResponse {
    val httpResponse = client.post("https://api.portone.io/payments") {
      url {
        appendPathSegments(paymentId, "register-store-receipt")
      }
      headers {
        append(HttpHeaders.Authorization, "PortOne ${this@PortOneApi.apiSecret}")
      }
      contentType(ContentType.Application.Json)
      accept(ContentType.Application.Json)
      userAgent("portone-server-sdk-jvm/${SDK_VERSION}")
      setBody(this@PortOneApi.json.encodeToString(RegisterStoreReceiptBody(items = items, storeId =
          storeId)))
    }
    if (httpResponse.status.value !in 200..299) {
      val httpBody = httpResponse.body<String>()
      val httpBodyDecoded = try {
        this.json.decodeFromString<RegisterStoreReceiptError>(httpBody)
      }
      catch (_: Exception) {
        throw UnrecognizedException("Unrecognized API error: $httpBody")
      }
      when (httpBodyDecoded) {
        is ForbiddenError -> throw ForbiddenException(message = httpBodyDecoded.message)
        is InvalidRequestError -> throw InvalidRequestException(message = httpBodyDecoded.message)
        is PaymentNotFoundError -> throw PaymentNotFoundException(message = httpBodyDecoded.message)
        is PaymentNotPaidError -> throw PaymentNotPaidException(message = httpBodyDecoded.message)
        is PgProviderError -> throw PgProviderException(message = httpBodyDecoded.message, pgCode =
            httpBodyDecoded.pgCode, pgMessage = httpBodyDecoded.pgMessage)
        is UnauthorizedError -> throw UnauthorizedException(message = httpBodyDecoded.message)
      }
    }
    val httpBody = httpResponse.body<String>()
    return try {
      this.json.decodeFromString<RegisterStoreReceiptResponse>(httpBody)
    }
    catch (_: Exception) {
      throw UnrecognizedException("Unrecognized API response: $httpBody")
    }
  }

  /**
   * @suppress
   */
  @JvmName("registerStoreReceipt")
  public fun registerStoreReceiptFuture(paymentId: String,
      items: List<RegisterStoreReceiptBodyItem>): CompletableFuture<RegisterStoreReceiptResponse> =
      GlobalScope.future { registerStoreReceipt(paymentId, items) }
}
