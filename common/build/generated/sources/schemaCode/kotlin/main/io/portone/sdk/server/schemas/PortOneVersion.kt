package io.portone.sdk.server.schemas

import kotlinx.serialization.Serializable

/**
 * 포트원 버전
 */
@Serializable
public enum class PortOneVersion {
  V1,
  V2,
}
