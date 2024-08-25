package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * CancelRequester
 */
@Serializable
public enum class CancelRequester {
  CUSTOMER,
  ADMIN,
}
