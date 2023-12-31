package com.example.reply.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reply.R
import com.example.reply.data.Email
import com.example.reply.data.MailboxType
import com.example.reply.ui.util.ReplyContentType
import com.example.reply.ui.util.ReplyNavigationType

@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: ReplyViewModel = viewModel()
    val replyUiState = viewModel.uiState.collectAsState().value
    var contentType = ReplyContentType.LIST_ONLY
    val navigationType =
        when (windowSize) {
            WindowWidthSizeClass.Compact -> {
                ReplyNavigationType.BOTTOM_NAVIGATION
            }

            WindowWidthSizeClass.Medium -> {
                ReplyNavigationType.NAVIGATION_RAIL
            }

            WindowWidthSizeClass.Expanded -> {
                contentType = ReplyContentType.LIST_AND_DETAIL
                ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
            }

            else -> {
                ReplyNavigationType.BOTTOM_NAVIGATION
            }
        }
    ReplyHomeScreen(
        navigationType = navigationType,
        contentType = contentType,
        replyUiState = replyUiState,
        onTabPressed = { mailboxType: MailboxType ->
            viewModel.updateCurrentMailbox(mailboxType = mailboxType)
            viewModel.resetHomeScreenStates()
        },
        onEmailCardPressed = { email: Email ->
            viewModel.updateDetailsScreenStates(
                email = email
            )
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreenStates()
        },
        modifier = modifier
    )
}
