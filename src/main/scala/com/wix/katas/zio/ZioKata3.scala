package com.wix.katas.zio

import com.wix.katas.zio.Blog.PostId
import zio.ZIO

trait ZioKata3 extends Blog {
  // Invoke "getPost" for all elements of "postIds" concurrently.
  // "Fail fast", that is fail if any of the "getPost" invocations fails.
  def getPostAuthors(postIds: List[PostId]): ZIO[Any, Exception, List[String]] =
    ZIO.foreachPar(postIds)(getPost(_).map(_.author))
}
