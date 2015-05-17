import java.util.Random
import com.nicta.rng.Rng

///

def rollDie0(): Int =
  new Random().nextInt(6) + 1

def rollDieTwice0(): Int =
  rollDie0() + rollDie0()

///

def rollDie(r: Random): Int =
  r.nextInt(6) + 1

def rollDieTwice(r: Random): Int =
  rollDie(r) + rollDie(r)

def useResult(r: Random) = {
  val res = rollDieTwice(r)
  if (res > 6) res * 1.2 else res * 0.9
}

def list(r: Random): List[Double] =
  List.fill(3)(useResult(r))

list(new Random)

val r = new Random()
r.setSeed(3)
list(r)

///

val rollDie2: Rng[Int] =
  Rng.chooseint(1, 7)

val rollDieTwice2: Rng[Int] =
  rollDie2.flatMap(d1 => rollDie2.map(d2 => d1 + d2))

val useResult2: Rng[Double] =
  rollDieTwice2.map(res => if (res > 6) res * 1.2 else res * 0.9)

val list2: Rng[List[Double]] =
  useResult2.fill(3)

list2.run.unsafePerformIO()
Rng.setseed(2).flatMap(_ => list2).run.unsafePerformIO()
