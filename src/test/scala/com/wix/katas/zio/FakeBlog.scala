package com.wix.katas.zio

import com.wix.katas.zio.Blog.{BlogId, BlogPost, BlogSettings, PostId, PostNotFound, SettingsNotFound}
import zio.ZIO

trait FakeBlog extends Blog {

  protected val blogPosts = Map(
    1 -> BlogPost(1, "author1", "content1"),
    2 -> BlogPost(2, "author2", "content2"),
    3 -> BlogPost(3, "", "content3")
  )

  private val blogSettings = Map(
    10 -> BlogSettings(10, "author10"),
    20 -> BlogSettings(20, "author20"),
    30 -> BlogSettings(30, "")
  )

  def getPost(postId: PostId): ZIO[Any, Exception, BlogPost] = blogPosts.get(postId) match {
    case Some(blogPost) => ZIO.succeed(blogPost)
    case None           => ZIO.fail(PostNotFound(postId))
  }

  def getSettings(blogId: BlogId): ZIO[Any, Exception, BlogSettings] = blogSettings.get(blogId) match {
    case Some(blogSettings) => ZIO.succeed(blogSettings)
    case None               => ZIO.fail(SettingsNotFound(blogId))
  }
}
