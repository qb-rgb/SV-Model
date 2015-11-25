/** Contain the classes to model a chemical reaction system. */
package object model {

  /** A molecule is represented by its name. */
  type Molecule = String

  /** A solution is a map which associate a molecule with its quantity in the
    * solution.
    */
  type Solution = Map[Molecule, Int]

}
