package studio.daily.minecraftlinker.feature.auth.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CodeInputField(
    codeLength: Int = 6,
    onCodeComplete: (String) -> Unit = {}
) {
    val focusRequesters = List(codeLength) { FocusRequester() }
    val code = remember { mutableStateListOf(*Array(codeLength) { "" }) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        horizontalArrangement = Arrangement.spacedBy(
            8.dp,
            Alignment.CenterHorizontally)
    ) {
        repeat(codeLength) { index ->
            OutlinedTextField(
                value = code[index],
                onValueChange = { value ->
                    val upperCaseValue = value.uppercase()
                    if (upperCaseValue.length <= 1) {
                        code[index] = upperCaseValue

                        if (upperCaseValue.isNotEmpty() && index < codeLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        if (code.joinToString("").length == codeLength && !code.contains("")) {
                            keyboardController?.hide()
                            onCodeComplete(code.joinToString(""))
                        }
                    }
                },
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { event ->
                        if (event.type == KeyEventType.KeyDown &&
                            event.key == Key.Backspace &&
                            code[index].isEmpty() &&
                            index > 0
                        ) {
                            code[index - 1] = ""
                            focusRequesters[index - 1].requestFocus()
                            true
                        } else {
                            false
                        }
                    },
                singleLine = true,
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Ascii,
                    imeAction = if (index == codeLength - 1) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            )
        }
    }
}