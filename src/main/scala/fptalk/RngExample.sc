import java.util.Random
import com.nicta.rng.Rng

///
// Zufall als Seiteneffekt und ohne einfache Möglichkeit wiederholbar
// zu testen.

def rollDie0(): Int =
  new Random().nextInt(6) + 1

def rollDieTwice0(): Int =
  rollDie0() + rollDie0()

def useResult0(): Double = {
  val res = rollDieTwice0()
  if (res > 6) res * 1.2 else res * 0.9
}

// Seiteneffekt + keine einfache Möglichkeit ein Seed vorzugeben
useResult0()

///
// immer noch Seiteneffekte, dafür kann deterministisch getestet werden
// Nachteil: Logik und "Dependency Injection" wird kombiniert

def rollDie1(r: Random): Int =
  r.nextInt(6) + 1

def rollDieTwice1(r: Random): Int =
  rollDie1(r) + rollDie1(r)

def useResult1(r: Random): Double = {
  val res = rollDieTwice1(r)
  if (res > 6) res * 1.2 else res * 0.9
}

useResult1(new Random)
val r = new Random()
r.setSeed(3)
useResult1(r)

///

val rollDie2: Rng[Int] =
  Rng.chooseint(1, 7)

val rollDieTwice2: Rng[Int] = for {
  d1 <- rollDie2
  d2 <- rollDie2
} yield d1 + d2

val useResult2: Rng[Double] =
  rollDieTwice2.map(res => if (res > 6) res * 1.2 else res * 0.9)

useResult2.run.unsafePerformIO()
Rng.setseed(2).flatMap(_ => useResult2).run.unsafePerformIO()
