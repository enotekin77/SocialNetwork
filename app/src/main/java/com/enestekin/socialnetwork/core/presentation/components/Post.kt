package com.enestekin.socialnetwork.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.enestekin.socialnetwork.R
import com.enestekin.socialnetwork.core.domain.models.Post
import com.enestekin.socialnetwork.core.presentation.ui.theme.*
import com.enestekin.socialnetwork.core.util.Constants.MAX_POST_DESCRIPTION_LINES

@ExperimentalCoilApi
@Composable
fun Post(
    post: Post,// we just send whole post for now  , but later post id
    modifier: Modifier = Modifier,
    showProfileImage: Boolean = true,
    onPostClicked: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onSharedClick: () -> Unit = {},
    onUsernameClick: () -> Unit = {},

) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(SpaceMedium)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(
                    y = if (showProfileImage) ProfilePictureSizeMedium / 2f else 0.dp
                ) // put the profile picture down half of it
                .clip(MaterialTheme.shapes.medium) // making photo circle
                .shadow(5.dp) // should be before background
                .background(MediumGray)
                .clickable {
                    onPostClicked()
                }

        ) {
            Image(
                painter = rememberImagePainter(
                    data = post.imageUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
               contentDescription = "Post Image"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceMedium)

            ) {
                ActionRow(
                    username = "Enes Tekin",
                    modifier = Modifier.fillMaxWidth(),
                    onLikeClick = onLikeClick,
                    onCommentClick =onCommentClick,
                    onSharedClick = onSharedClick,
                    onUsernameClick = onUsernameClick
                )
                Spacer(modifier = Modifier.height(SpaceSmall))

                Text(
                    text = buildAnnotatedString {
                        append(post.description)
                        withStyle(
                            SpanStyle(
                                color = HintGray
                            )
                        ) {
                            append(
                                LocalContext.current.getString(
                                    R.string.read_more
                                )
                            )
                        }
                    },
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = MAX_POST_DESCRIPTION_LINES
                )

                Spacer(modifier = Modifier.height(SpaceMedium))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.x_likes,
                            post.likeCount
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2
                    )

                    Text(
                        text = stringResource(
                            id = R.string.x_comments,
                            post.commentCount
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2
                    )

                }
            }
            }

        if (showProfileImage) {
            //Compose rule ->  Last drawing is on top
            Image(
                rememberImagePainter(
                    data = post.profilePictureUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(ProfilePictureSizeMedium)
                    .clip(CircleShape) // making photo circle
                    .align(Alignment.TopCenter)
            )
        }
    }

}

@Composable
fun EngagementButtons(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    iconSize: Dp = 30.dp,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onSharedClick: () -> Unit = {}
) {

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        Spacer(modifier = Modifier.width(SpaceMedium))

        IconButton(
            onClick = { onLikeClick() },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = if (isLiked) MaterialTheme.colors.primary else TextWhite,
                contentDescription = if (isLiked) {
                    stringResource(id = R.string.unlike)
                } else {
                    stringResource(id = R.string.like)
                }
            )

        }
        Spacer(modifier = Modifier.width(SpaceMedium))
        IconButton(

            onClick = { onCommentClick() },
            modifier = Modifier.size(iconSize)

        ) {
            Icon(
                imageVector = Icons.Filled.Comment,
                contentDescription = stringResource(id = R.string.comment)
            )

        }
        Spacer(modifier = Modifier.width(SpaceMedium))

        IconButton(
            onClick = { onSharedClick() },
            modifier = Modifier.size(iconSize)

        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(id = R.string.share)
            )

        }
    }

}

@Composable
fun ActionRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onSharedClick: () -> Unit = {},
    username: String,
    onUsernameClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, //push the section left and right side
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = username,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier
                .clickable {
                    onUsernameClick()
                }
        )


        EngagementButtons(

            isLiked = isLiked,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
            onSharedClick = onSharedClick
        )

    }
}