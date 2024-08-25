package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * 결제수단 정보
 */
@Serializable
@JsonClassDiscriminator("type")
public sealed interface PaymentMethod
