package com.andrew.social.login.core

import java.nio.charset.StandardCharsets
import java.util.*

/**
 * Created by andreymatyushin on 18.09.2018.
 */

fun randomUTF(): String = String(ByteArray(15).apply { Random().nextBytes(this) }, StandardCharsets.UTF_8)