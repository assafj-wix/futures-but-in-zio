# Exercises on Scala Futures - But in ZIO

## Introduction
***
This exercise is a port of the [Scala Futures](https://github.com/michaelwix/futures), implemented in [ZIO](https://zio.dev/).</br>
Michael's _Scala Futures_ is a great exercise to learn about Scala's `Future`, so I decided to port it to ZIO and compare the code.

> `Futures` provide a simple way to run an asynchronous computation. `Future` starts a computation when you create it and then eventually returns the result. For example, every RPC invocation at Wix is a function that returns a `Future` of the RPC service result. See more information about `Futures` API in the [documentation](https://www.scala-lang.org/api/2.13.10/scala/concurrent/Future.html)
>
> `Futures` are composable. We consider the following common cases of `Futures` composition:
>
> Sequential composition with or without error accumulation;
> Concurrent composition with or without error accumulation;
> See the details below.
> 
> ## Exercises
> ***
> ### Kata1 "Fallback"
> This is an exercise on the sequential Futures composition without error accumulation. This exercise uses composition of both sync and async operations.
> 
> ### Kata2 "Retry"
> This is an exercise on the sequential Futures composition with error accumulation. This exercise uses a recursion although a non-recursive solution would be better.
> 
> ### Kata3 "Traverse"
> This is an exercise on the concurrent Futures composition without error accumul1ation. This is just a straightforward use of `Future.traverse`.
> 
> ### Kata4 and Kata5 "Traverse with errors accumulation"
> These are exercises on the concurrent Futures composition with error accumulation.
