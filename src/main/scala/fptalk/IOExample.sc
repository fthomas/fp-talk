import scalaz.effect.IO


case class A(a: String)

//Seiteneffekt wird durch IO-Typ explizit gemacht
//IO hält eine Funktion die Objekt vom Typ A zurückliefert
def findA(a: String): IO[A] = IO {
  A(a)
}

//IO[Unit] deutet an, dass 'nichts' zurückgegeben wird -> Schreiboperation
def save(a: A): IO[Unit] = IO{ println(s"println: save ${a}")}

//Businesslogik - frei von Seiteneffekten
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

//context in dem die Businesslogik ausgeführt wird
def interpreter(a: String): IO[A] = {
  for {
    a <- findA(a)
    processed = pureBusinessLogic(a)
    _ <- save(processed)
  } yield processed
}

//Seiteneffekte werden hier noch nicht ausgeführt!
//Bis hier hin erfolgt nur eine Verkettung von Funktionen
val run = interpreter("asdf")

//Führe Seiteneffekte aus und wende Businesslogik an
run.unsafePerformIO()
