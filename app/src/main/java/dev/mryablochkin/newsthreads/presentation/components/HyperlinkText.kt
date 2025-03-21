package dev.mryablochkin.newsthreads.presentation.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

@Deprecated(
    message = "Use HyperlinkText2 composable function without deprecated ClickableText",
    level = DeprecationLevel.WARNING
)
@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    text: String,
    linkText: List<String>,
    urls: List<String>,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    linkTextColor: Color = MaterialTheme.colorScheme.primary,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
) {
    val uriHandler = LocalUriHandler.current

    val annotatedString = buildAnnotatedString {
        append(text).apply {
            addStyle(
                style = SpanStyle(color = textColor),
                start = 0,
                end = text.length
            )
        }
        linkText.forEachIndexed { index, link ->
            val startIndex = text.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(color = linkTextColor, textDecoration = linkTextDecoration),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation("URL", urls[index], startIndex, endIndex)
        }
        addStyle(SpanStyle(fontSize = fontSize, fontWeight = fontWeight), 0, text.length)
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            annotatedString.getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}

@Composable
fun HyperlinkText2(
    modifier: Modifier = Modifier,
    text: String,
    linkText: List<String>,
    urls: List<String>,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    linkTextColor: Color = MaterialTheme.colorScheme.primary,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
) {
    val annotatedString = buildAnnotatedString {
        append(text).apply { addStyle(SpanStyle(textColor), 0, text.length) }
        linkText.forEachIndexed { index, link ->
            val startIndex = text.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(color = linkTextColor, textDecoration = linkTextDecoration),
                start = startIndex,
                end = endIndex
            )
            addLink(LinkAnnotation.Url(urls[index]), startIndex, endIndex)
        }
        addStyle(SpanStyle(fontSize = fontSize, fontWeight = fontWeight), 0, text.length)
    }

    BasicText(text = annotatedString, modifier = modifier)
}

