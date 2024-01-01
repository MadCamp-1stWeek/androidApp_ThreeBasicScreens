package com.heewoong.threebasicscreens

class SnakePoints {

    private var positionX = 0
    private var positionY = 0
    constructor(PositionX: Int, PositionY: Int) {
        this.positionX = PositionX
        this.positionY = PositionY
    }
    constructor() {}

    fun getPositionX(): Int {
        return positionX
    }

    fun getPositionY(): Int {
        return positionY
    }
    fun setPositionX(PositionX: Int) {
        positionX = PositionX
    }
    fun setPositionY(PositionY: Int) {
        positionY = PositionY
    }
}