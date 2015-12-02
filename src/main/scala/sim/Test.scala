import model._
import sim._

object Test {

  def main(args: Array[String]): Unit = {
    val initSol = Map("A" -> 6, "B" -> 3, "C" -> 1)

    val react1 = Map("A" -> 1, "B" -> 2)
    val prod1 = Map("C" -> 1)
    val r1 = Reaction(react1, prod1, 1)

    val react2 = Map("C" -> 1, "A" -> 2)
    val prod2 = Map("B" -> 3)
    val r2 = Reaction(react2, prod2, 1)

    val simulator = new Simulator(initSol, Set(r1, r2), 3)

    simulator.simule
  }

}
