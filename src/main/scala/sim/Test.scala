import model._
import sim._

object Test {

  def main(args: Array[String]): Unit = {
    val initSol = Map("S" -> 100000, "E" -> 0, "P" -> 0)

    val react1 = Map("S" -> 1, "E" -> 1)
    val prod1 = Map("P" -> 1, "E" -> 1)
    val r1 = Reaction(react1, prod1, 0.1)

    val simulator = new Simulator(initSol, Set(r1), 10)

    simulator.simule
  }

}
