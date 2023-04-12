package com.wix.katas.zio

import com.wix.katas.zio.Blog.{BlogPost, PostId}
import com.wix.katas.zio.ZioKata2.{GetPostFailure, GetPostSuccess}
import zio.ZIO
import zio.test.Assertion._
import zio.test.{ZIOSpecDefault, _}

object ZioKata2Test extends ZIOSpecDefault {

  // ------------------------------------------------------------------------------------------------------------------
  // Test Scope
  // ------------------------------------------------------------------------------------------------------------------

  case class GetPostTempException(postId: PostId, attempt: Int) extends Exception(s"getPost failed on attempt $attempt")

  class Kata2Blog(maxFailures: Int) extends ZioKata2 with FakeBlog {

    private var failures = 0

    override def getPost(postId: PostId): ZIO[Any, Exception, BlogPost] = for {
      _ <- ZIO.succeed(failures += 1)
      _ <- ZIO.debug(s"getPost($postId) - attempt $failures/$maxFailures")

      _ <- ZIO.when(failures <= maxFailures)(ZIO.fail(GetPostTempException(postId, failures)))

      post <- super.getPost(postId)
    } yield post
  }

  val postId = 1
  val post   = BlogPost(postId, "author1", "content1")

  override def spec = suite("ZioKata2 should")(
    test("get post on the 1st attempt") {
      val blog     = new Kata2Blog(maxFailures = 0)
      val expected = GetPostSuccess(post, Nil)

      assertZIO(blog.getPostWithRetries(postId = 1, maxAttempts = 10))(equalTo(expected))
    },
    test("get post on the 2nd attempt of 3") {
      val blog     = new Kata2Blog(maxFailures = 1)
      val expected = GetPostSuccess(post, List(GetPostTempException(postId, attempt = 1)))

      assertZIO(blog.getPostWithRetries(postId = 1, maxAttempts = 3))(equalTo(expected))
    },
    test("get post on the 3d attempt of 3") {
      val blog     = new Kata2Blog(maxFailures = 2)
      val expected =
        GetPostSuccess(post, List.tabulate(2)(attempt => GetPostTempException(postId, attempt = attempt + 1)))

      assertZIO(blog.getPostWithRetries(postId, maxAttempts = 3))(equalTo(expected))
    },
    test("fail to get post after 1 attempt") {
      val blog     = new Kata2Blog(maxFailures = 10)
      val expected = GetPostFailure(List(GetPostTempException(postId, attempt = 1)))

      assertZIO(blog.getPostWithRetries(postId, maxAttempts = 1))(equalTo(expected))
    },
    test("fail to get post after 2 attempts") {
      val blog     = new Kata2Blog(maxFailures = 10)
      val expected = GetPostFailure(List.tabulate(2)(attempt => GetPostTempException(postId, attempt = attempt + 1)))

      assertZIO(blog.getPostWithRetries(postId, maxAttempts = 2))(equalTo(expected))
    }
  )

}
