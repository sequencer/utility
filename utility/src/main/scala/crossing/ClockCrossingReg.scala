package org.chipsalliance.utils.crossing

import chisel3._
import chisel3.util.RegEnable

class ClockCrossingReg(w: Int = 1, doInit: Boolean) extends Module {

  override def desiredName = s"ClockCrossingReg_w${w}"

  val io = IO(new Bundle {
    val d = Input(UInt(w.W))
    val q = Output(UInt(w.W))
    val en = Input(Bool())
  })

  val cdc_reg =
    if (doInit) RegEnable(next = io.d, init = 0.U(w.W), enable = io.en)
    else RegEnable(next = io.d, enable = io.en)
  io.q := cdc_reg
}

object ClockCrossingReg {
  def apply[T <: Chisel.Data](
    in:     T,
    en:     Bool,
    doInit: Boolean,
    name:   Option[String] = None
  ): T = {
    val cdc_reg = Module(new ClockCrossingReg(in.getWidth, doInit))
    name.foreach { cdc_reg.suggestName(_) }
    cdc_reg.io.d := in.asUInt
    cdc_reg.io.en := en
    cdc_reg.io.q.asTypeOf(in)
  }
}
