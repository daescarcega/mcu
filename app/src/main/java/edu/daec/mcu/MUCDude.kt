package edu.daec.mcu

class MUCDude(val alias:String, val notes:String){
    public var name:String?= null
    public var hnotes:String?= null
    init {
        name = alias
        hnotes = notes
    }
}