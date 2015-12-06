package sim

import model.{Solution, Reaction}

/** Simule an chemical reaction system.
  *
  * @constructor create a new Simulator.
  * @param initialSolution initial solution of the system
  * @param reactions reactions of the system
  * @param maxTime maximum time to reach in the simulation
  */
class Simulator(
  initialSolution: Solution,
  reactions: Set[Reaction],
  maxTime: Double
) {

  import scala.util.Random
  import java.io.{File, FileWriter, BufferedWriter}

  // Ordered species of the initial solution
  private val orderedInit = this.initialSolution.keys.toList.sorted

  // Get the h coefficient for a reaction in a particular solution
  private def getHFor(reaction: Reaction, solution: Solution): BigInt = {
    // Factorial
    def fact(n: BigInt): BigInt = if (n <= 0) 1 else n * fact(n - 1)

    // Under
    def under(n: BigInt, k: BigInt): BigInt = (n to n - k + 1 by -1).foldLeft(BigInt(1)){ _ * _ }

    // Binomial coefficient (k among n)
    def binomialCoeff(n: BigInt, k: BigInt): BigInt = under(n, k) / fact(k)

    reaction.react.keys.foldLeft(BigInt(1)){
      case (acc, mol) =>
        acc * binomialCoeff(BigInt(solution.getOrElse(mol, 0)), BigInt(reaction.react(mol)))
    }
  }

  // Determine if some reaction can occure
  private def someReaction(solution: Solution): Boolean =
    this.reactions exists { _ canApplyOn solution }

  // Write the state in a file
  private def writeInFile(time: Double, current: Solution, bw: BufferedWriter): Unit =
    bw.write(
      time + "\t" +
      ((this.orderedInit map { current.getOrElse(_, 0) }) mkString "\t") + "\n"
    )

  // Write a plot file for the reactions system
  private def writePlotFile: Unit = {
    val file = new File("sim.plot")
    val bw = new BufferedWriter(new FileWriter(file))
    val n = this.initialSolution.keys.size

    bw.write("set terminal 'png'\n")
    bw.write("set output 'sim.png'\n")
    bw.write("plot ")

    for (i <- 2 to n + 1) {
      bw.write("\"sim.dat\" using 1:" + i + " with lines title \'" + this.orderedInit(i - 2) + "\'")

      if (i < n + 1)
        bw.write(", ")
    }

    bw.close
  }

  /** Simule the reactions system. */
  def simule: Unit = {
    val file = new File("sim.dat")
    val bw = new BufferedWriter(new FileWriter(file))

    def innerSimule(currentSolution: Solution, time: Double): Unit = {
      // Write the datas
      writeInFile(time, currentSolution, bw)

      if (time < this.maxTime && (this someReaction currentSolution)) {
        val hiTimesAi = (
          for {
            reac <- this.reactions
          } yield (reac, this.getHFor(reac, currentSolution).toDouble * reac.k)
        ).toMap
        val sumHiAi = hiTimesAi.values.sum
        val tau = (1 / sumHiAi) * math.log(1 / Random.nextDouble)

        def selectReac(reactions: List[(Reaction, Double)], currentNb: Double): Reaction =
          if (reactions.length == 1)
            reactions.head._1
          else {
            val (reac, hiai) = reactions.head
            val newNb = currentNb - hiai

            if (newNb <= 0)
              reac
            else
              selectReac(reactions.tail, newNb)
          }

        val reacSelec = selectReac(hiTimesAi.toList, Random.nextDouble * sumHiAi)

        innerSimule(reacSelec applyOn currentSolution, time + tau)
      }
    }

    innerSimule(this.initialSolution, 0)
    bw.close
    this.writePlotFile
  }

}
