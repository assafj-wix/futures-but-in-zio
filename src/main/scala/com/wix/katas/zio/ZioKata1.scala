package com.wix.katas.zio

import com.wix.katas.zio.Blog.{BlogId, EmptyAuthor, PostId}
import zio.ZIO

trait ZioKata1 extends Blog {

  // Invoke "getPost" and if the post author is empty then invoke "getSettings"
  // and if the settings author is empty then fail with exception "EmptyAuthor".
  // Fail if either "getPost" or "getSettings" fails.
  def getNonEmptyAuthor(postId: PostId, blogId: BlogId): ZIO[Any, Exception, String] = for {
    author1 <- getPost(postId).map(_.author)
    author2 <- if (author1.nonEmpty) ZIO.succeed(author1) else getSettings(blogId).map(_.author)
    author3 <- if (author2.nonEmpty) ZIO.succeed(author2) else ZIO.fail(EmptyAuthor(postId, blogId))
  } yield author3
}
