package com.enestekin.socialnetwork.feature_post.domain.use_case

data class PostUseCases(
 val getPostsForFollowsUserCase: GetPostsForFollowsUseCase,
 val createPostUseCase: CreatePostUseCase,
 val getPostDetails: GetPostDetailUseCase,
 val getCommentsForPost: GetCommentsForPostUseCase
)