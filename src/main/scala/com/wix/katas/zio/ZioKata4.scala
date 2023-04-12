package com.wix.katas.zio

import com.wix.katas.zio.Blog.PostId
import zio.ZIO

import scala.util.{Failure, Success, Try}

trait ZioKata4 extends Blog {

  // Invoke "getPost" for all elements of "postIds" concurrently and
  // accumulate the results of all "getPost" invocations and return these results
  def getPostAuthors(postIds: List[PostId]): ZIO[Any, Nothing, List[Try[String]]] =
    ZIO.foreachPar(postIds)(
      getPost(_)
        .map(_.author)
        .map(Success(_))
        .catchAll(e => ZIO.succeed(Failure(e)))
    )
  //    ZIO.foreachPar(postIds)(getPost(_).map(_.author).exit.map(_.toTry))
}
