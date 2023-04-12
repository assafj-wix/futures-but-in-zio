package com.wix.katas.zio

import com.wix.katas.zio.Blog.PostId
import zio.ZIO

import scala.util.Try

trait ZioKata5 extends Blog {

  // Suppose "postIds" contains duplicates, so there is no need to invoke "getPost" for _each_ element of "postIds".
  // Thus invoke "getPost" only for _unique_ elements of "postIds" concurrently and return the results for all "postIds".
  def getPostAuthorsWithDuplicates(postIds: List[PostId]): ZIO[Any, Nothing, List[Try[String]]] =
    ZIO
      .foreachPar(postIds.distinct)(getPostAuthor)
      .map(_.toMap)
      .map(postIds.map(_))

  def getPostAuthor(postId: PostId): ZIO[Any, Nothing, (PostId, Try[String])] =
    getPost(postId)
      .map(_.author)
      .exit
      .map(_.toTry)
      .map(postId -> _)

}
