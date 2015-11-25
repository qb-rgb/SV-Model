package model

/** Represent a chemical reaction.
  *
  * @constructor create a new Reaction.
  * @param react reactants solution
  * @param prod products solution
  * @param k velocity of the reaction
  */
case class Reaction(val react: Solution, val prod: Solution, val k: Int)
