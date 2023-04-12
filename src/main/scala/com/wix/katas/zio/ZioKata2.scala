package com.wix.katas.zio

import com.wix.katas.zio.Blog.{BlogPost, PostId}
import com.wix.katas.zio.ZioKata2.{GetPostFailure, GetPostResult, GetPostSuccess}
import zio.{Ref, Schedule, ZIO}

trait ZioKata2 extends Blog {
  // ------------------------------------------------------------------------------------------------------------------
  // getPostWithRetries
  // ------------------------------------------------------------------------------------------------------------------

  // Invoke "getPost" and retry it if "getPost" fails until the number of invocations == "maxAttempts"
  def getPostWithRetries(postId: PostId, maxAttempts: Int): ZIO[Any, Exception, GetPostResult] = for {
    ref    <- Ref.make(List.empty[Throwable])
    result <- getPost(postId)
                .tapError(e => ref.update(_ :+ e))
                .retry(Schedule.recurs(maxAttempts - 1))
                .flatMap(post => ref.get.map(GetPostSuccess(post, _)))
                .catchAll(_ => ref.get.map(GetPostFailure))
  } yield result
}

object ZioKata2 {
  // ------------------------------------------------------------------------------------------------------------------
  // GetPostResult
  // ------------------------------------------------------------------------------------------------------------------

  sealed trait GetPostResult

  case class GetPostSuccess(post: BlogPost, exceptions: List[Throwable]) extends GetPostResult

  case class GetPostFailure(exceptions: List[Throwable]) extends GetPostResult
}
