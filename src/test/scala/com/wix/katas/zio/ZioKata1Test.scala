package com.wix.katas.zio

import com.wix.katas.zio.Blog.{EmptyAuthor, PostNotFound, SettingsNotFound}
import zio.test.Assertion._
import zio.test._

object ZioKata1Test extends ZIOSpecDefault {
  class Kata1Blog extends com.wix.katas.zio.ZioKata1 with FakeBlog

  val blog = new Kata1Blog()

  def spec = suite("ZioKata1 should")(
    test("get post author successfully") {
      for {
        author <- blog.getNonEmptyAuthor(postId = 1, blogId = 10)
      } yield assert(author)(equalTo("author1"))
    },
    test("fail to get author if getPost failed") {
      for {
        exit <- blog.getNonEmptyAuthor(postId = 5, blogId = 10).exit
      } yield assert(exit)(fails(equalTo(PostNotFound(postId = 5))))
    },
    test("get settings author if post author is empty") {
      assertZIO(blog.getNonEmptyAuthor(postId = 3, blogId = 10))(equalTo("author10"))
    },
    test("fail to get author because settings are not found") {
      assertZIO(blog.getNonEmptyAuthor(postId = 3, blogId = 50).exit)(fails(equalTo(SettingsNotFound(blogId = 50))))
    },
    test("fail to get author because settings author is empty") {
      assertZIO(blog.getNonEmptyAuthor(postId = 3, blogId = 30).exit)(fails(equalTo(EmptyAuthor(postId = 3, blogId = 30))))
    }
  )
}
