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
  val react: Solution,
  val prod: Solution,
  val k: Int,
  val a: Int
) {
}
