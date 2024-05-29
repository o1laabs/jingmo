package com.hefengbao.jingmo.data.model

import com.hefengbao.jingmo.data.database.entity.IdiomEntity
import kotlinx.serialization.Serializable

/**
 * 成语 v2
 */
@Serializable
data class Idiom(
    val id: Int,
    val word: String,
    val pinyin: String,
    val abbr: String,
    val explanation: String?,
    val source: IdiomSource?,
    val quote: IdiomQuote?,
    val example: IdiomExample?,
    val similar: List<String>?,
    val opposite: List<String>?,
    val usage: String?,
    val story: List<String>?,
    val notice: String?,
)

@Serializable
data class IdiomSource(
    val text: String? = null,
    val book: String? = null,
)

@Serializable
data class IdiomQuote(
    val text: String? = null,
    val book: String? = null,
)

@Serializable
data class IdiomExample(
    val text: String? = null,
    val book: String? = null,
)

fun Idiom.asIdiomEntity() = IdiomEntity(
    id = id,
    word = word,
    pinyin = pinyin,
    abbr = abbr,
    explanation = explanation,
    source = source,
    quote = quote,
    example = example,
    similar = similar,
    opposite = opposite,
    usage = usage,
    story = story,
    notice = notice
)
