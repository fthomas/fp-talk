import scalaz.effect.IO


case class A(a: String)

def findA(a: String): IO[A] = IO {
  A(a)
}

def save(a: A): IO[Unit] = IO{ println(s"println: save ${a}")}

def pureBusinessLogic(a: A): A = {
  val trim = (a: String) => a.trim
  val toUppercase = (a: String) => a.toUpperCase
  val removeD = (a: String) => a.replaceAll("d", "")

  A(
    trim
    .andThen(removeD)
    .andThen(toUppercase)(a.a)
  )
}

def interpreter(a: String): IO[A] = {
  for {
    a <- findA(a)
    processed = pureBusinessLogic(a)
    _ <- save(processed)
  } yield processed
}

val run = interpreter("asdf")

run.unsafePerformIO()
