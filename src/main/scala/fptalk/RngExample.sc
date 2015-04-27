import java.util.Random

import com.nicta.rng.Rng

case class Point(x: Double, y: Double)

val d: Rng[Double] = Rng.double

val point: Rng[Point] =
  for {
    x <- d
    y <- d
  } yield Point(x, y)


def norm(p: Point): Double =
  math.sqrt(p.x * p.x + p.y + p.y)

val n: Rng[Double] = point.map(norm)

Rng.setseed(0).flatMap(_ => n).run.unsafePerformIO()
n.run.unsafePerformIO()


point.run.unsafePerformIO()



val r = new Random

def randomPoint(rand: Random): Point =
  Point(rand.nextDouble(), rand.nextDouble())

norm(randomPoint(new Random(0)))
randomPoint(r)
randomPoint(r)


