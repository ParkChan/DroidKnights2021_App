package com.droidknights.app2021.shared

import kotlinx.serialization.Serializable

@JvmInline
value class JsonRawString(val value: String)

@Serializable
@JvmInline
value class HexColor(val value: String)