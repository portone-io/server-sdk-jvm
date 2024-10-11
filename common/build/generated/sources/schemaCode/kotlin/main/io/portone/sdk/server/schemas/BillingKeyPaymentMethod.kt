package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * 빌링키 발급 수단 정보
 */
@Serializable
@JsonClassDiscriminator("type")
public sealed interface BillingKeyPaymentMethod
