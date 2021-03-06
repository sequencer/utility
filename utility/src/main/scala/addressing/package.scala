package org.chipsalliance.utils

package object addressing {

  def bitIndexes(x: BigInt, tail: Seq[Int] = Nil): Seq[Int] = {
    require(x >= 0)
    if (x == 0) {
      tail.reverse
    } else {
      val lowest = x.lowestSetBit
      bitIndexes(x.clearBit(lowest), lowest +: tail)
    }
  }

}
