package com.wix.katas.zio

import zio.ZIO

trait Blog {

  import Blog._

  def getPost(postId: PostId): ZIO[Any, Exception, BlogPost]

  def getSettings(blogId: BlogId): ZIO[Any, Exception, BlogSettings]
}

object Blog {

  type BlogId = Int

  type PostId = Int

  case class BlogSettings(blogId: BlogId, author: String)

  case class BlogPost(postId: PostId, author: String, content: String)

  case class PostNotFound(postId: PostId) extends Exception(s"post [$postId] not found")

  case class SettingsNotFound(blogId: BlogId) extends Exception(s"settings [$blogId] not found")

  case class EmptyAuthor(postId: PostId, blogId: BlogId) extends Exception(s"author of [$postId/$blogId] is empty")
}
