package com.wix.katas.zio

import com.wix.katas.zio.Blog.PostNotFound
import zio.test.Assertion.{equalTo, fails}
import zio.test.{assertZIO, ZIOSpecDefault}

import scala.util.{Failure, Success}

object ZioKata4Test extends ZIOSpecDefault {
  class Kata4Blog extends ZioKata4 with FakeBlog

  val blog = new Kata4Blog()

  override def spec = suite("ZioKafka4 should")(
    test("get authors of posts 1, 2, 3") {
      val postIds  = List(1, 2, 3)
      val expected = List("author1", "author2", "").map(Success(_))
      assertZIO(blog.getPostAuthors(postIds))(equalTo(expected))
    },
    test("fail because of a not found post") {
      val postIds  = List(1, 2, 4)
      val expected = List(Success("author1"), Success("author2"), Failure(PostNotFound(postId = 4)))
      assertZIO(blog.getPostAuthors(postIds))(equalTo(expected))
    }
  )
}
