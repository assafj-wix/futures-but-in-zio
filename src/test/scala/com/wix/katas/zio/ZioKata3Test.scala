package com.wix.katas.zio

import com.wix.katas.zio.Blog.PostNotFound
import zio.test.Assertion.{equalTo, fails}
import zio.test.{ZIOSpecDefault, assertZIO}

object ZioKata3Test extends ZIOSpecDefault {

  class Kata3Blog extends ZioKata3 with FakeBlog

  val blog = new Kata3Blog()

  override def spec = suite("ZioKafka3 should")(
    test("get authors of posts 1, 2, 3") {
      val postIds  = List(1, 2, 3)
      val expected = List("author1", "author2", "")
      assertZIO(blog.getPostAuthors(postIds))(equalTo(expected))
    },
    test("fail because of a not found post") {
      val postIds  = List(1, 2, 4)
      assertZIO(blog.getPostAuthors(postIds).exit)(fails(equalTo(PostNotFound(postId = 4))))
    },
  )
}
