package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * 간편 결제 수단
 */
@Serializable
@JsonClassDiscriminator("type")
public sealed interface PaymentMethodEasyPayMethod
