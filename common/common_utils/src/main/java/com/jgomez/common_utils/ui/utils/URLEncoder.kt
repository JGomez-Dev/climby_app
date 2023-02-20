package com.jgomez.common_utils.ui.utils

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.encodeUrl(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())