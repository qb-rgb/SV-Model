import model._
import sim._

object Test {

  def main(args: Array[String]): Unit = {
    val initSol = Map("A" -> 100000)

    val react1 = Map("A" -> 2)
    val prod1: Solution = Map()
    val r1 = Reaction(react1, prod1, 0.002)

    // val react2 = Map("C" -> 1, "A" -> 2)
    // val prod2 = Map("B" -> 3)
    // val r2 = Reaction(react2, prod2, 1)

    val simulator = new Simulator(initSol, Set(r1), 0.01)

    simulator.simule
  }

}
