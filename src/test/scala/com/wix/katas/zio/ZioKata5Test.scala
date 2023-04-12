package com.wix.katas.zio

import com.wix.katas.zio.Blog.{BlogPost, PostId}
import zio.test.Assertion.equalTo
import zio.test.{assert, ZIOSpecDefault}
import zio.{Ref, ZIO}

import scala.util.Success

object ZioKata5Test extends ZIOSpecDefault {
  class Kata5Blog(ref: Ref[Map[PostId, Int]]) extends ZioKata5 with FakeBlog {
    override def getPost(postId: PostId): ZIO[Any, Exception, BlogPost] = for {
      _      <- allowGetPostOnlyOnce(postId) // mutual exclusion point
      author <- super.getPost(postId)
    } yield author

    private def allowGetPostOnlyOnce(postId: PostId) = {
      ref
        .updateAndGet(map => map + (postId -> (map.getOrElse(postId, 0) + 1)))
        .map(_(postId) > 1)
        .flatMap(ZIO.when(_)(ZIO.fail(new RuntimeException(s"post [$postId] already read"))))
    }
  }

  object Kata5Blog {
    def apply(): ZIO[Any, Nothing, Kata5Blog] =
      Ref
        .make(Map.empty[PostId, Int])
        .map(new Kata5Blog(_))
  }

  override def spec = suite("ZioKafka5 should")(
    test("get authors of unique posts 1, 2, 3") {
      val postIds  = List(1, 2, 3)
      val expected = List("author1", "author2", "").map(Success(_))
      for {
        blog    <- Kata5Blog()
        authors <- blog.getPostAuthorsWithDuplicates(postIds)
      } yield assert(authors)(equalTo(expected))
    },
    test("get authors of non-unique posts 1, 2, 1") {
      val postIds  = List(1, 2, 1)
      val expected = List("author1", "author2", "author1").map(Success(_))
      for {
        blog    <- Kata5Blog()
        authors <- blog.getPostAuthorsWithDuplicates(postIds)
      } yield assert(authors)(equalTo(expected))
    }
  )
}
