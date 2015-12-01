package model

/** Represent a chemical reaction.
  *
  * @constructor create a new Reaction.
  * @param react reactants solution
  * @param prod products solution
  * @param k velocity of the reaction
  * @param a probability that the reaction occur
  */
case class Reaction(
  react: Solution,
  prod: Solution,
  k: Int,
  a: Int
) {

  /** Determine if the reaction can be apply on a given solution.
    *
    * @param solution solution on which apply the reaction
    * @return true is the reaction can be apply on the solution, false otherwise
    */
  def canApplyOn(solution: Solution): Boolean =
    this.react forall {
      case (molecule, amount) =>
        (solution contains molecule) && (solution(molecule) >= amount)
    }

  /** Apply the reaction on a solution.
    *
    * @param solution solution on which apply the reaction
    * @return the given solution if the reaction can not be applied on it,
    *         the application result of the reaction on the given solution
    *         otherwise
    */
  def applyOn(solution: Solution): Solution =
    if (this canApplyOn solution) {
      val withoutReacts = this.react.foldLeft(solution) {
        case (sol, (molecule, amount)) =>
          sol.updated(molecule, sol(molecule) - amount)
      }
      val withProducts = this.prod.foldLeft(withoutReacts) {
        case (sol, complex@(molecule, amount)) =>
          if (sol contains molecule)
            sol.updated(molecule, sol(molecule) + amount)
          else
            sol + complex
      }

      withProducts filterNot { case (_, amount) => amount <= 0 }
    } else
      solution

}
