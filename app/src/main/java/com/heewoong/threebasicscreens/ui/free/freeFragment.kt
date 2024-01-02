package com.heewoong.threebasicscreens.ui.free

import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.heewoong.threebasicscreens.OnSwipeTouchListener_game
import com.heewoong.threebasicscreens.R
import com.heewoong.threebasicscreens.SnakePoints
import java.util.Random
import java.util.Timer
import java.util.TimerTask


class freeFragment :Fragment()  {
    private lateinit var surfaceView: SurfaceView
    private lateinit var currentScore: TextView
    private lateinit var bestScore: TextView

    private lateinit var surfaceHolder: SurfaceHolder
    private var bestScorevalue: Int = 0

    private var snakePointsList: List<SnakePoints> = ArrayList<SnakePoints>()
    private var movingPosition: String = "right"
    private var score: Int = 0
    private var positionX: Int = 0
    private var positionY: Int = 0



    private val pointSize: Int = 30
    private val defaultTalePoints = 3;
    private val snakeColor = Color.MAGENTA
    private val snakeMovingSpeed = 800
    private lateinit var timer: Timer

    private var canvas: Canvas? = null
    private var pointColor: Paint? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_free, container, false)

        surfaceView = view.findViewById(R.id.surfaceView)
        currentScore = view.findViewById(R.id.current_score)
        bestScore = view.findViewById(R.id.best_score)

        surfaceHolder = surfaceView.holder
        bestScorevalue = bestScore.text.toString().substringAfter(": ").trim().toIntOrNull() ?: 0

        surfaceHolder.addCallback(SurfaceCallback())

        surfaceView.setOnTouchListener(object: OnSwipeTouchListener_game(requireActivity()) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                if (movingPosition != "right") {
                    movingPosition = "left"
                }
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                if (movingPosition != "left") {
                    movingPosition = "right"
                }
            }

            override fun onSwipeUp() {
                super.onSwipeUp()
                if (movingPosition != "down") {
                    movingPosition = "up"
                }
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
                if (movingPosition != "up") {
                    movingPosition = "down"
                }
            }
        })

        return view
    }

    inner class SurfaceCallback : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            // Surface의 상태가 변경되었을 때 호출되는 메서드
            surfaceHolder = holder
            init()
        }

        override fun surfaceCreated(holder: SurfaceHolder) {
            // Surface가 처음으로 생성되었을 때 호출되는 메서드
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            // Surface가 소멸되었을 때 호출되는 메서드
        }

        private fun init() {
            //clear snake points
            snakePointsList = ArrayList<SnakePoints>()
            currentScore.setText("0")
            score = 0
            movingPosition = "right"

            //default starting position
            var startPositionX = (pointSize) * defaultTalePoints

            for (i in 0..defaultTalePoints) {
                var snakePoint = SnakePoints(startPositionX, pointSize)
                (snakePointsList as ArrayList<SnakePoints>).add(snakePoint)

                //Increase value for next point
                startPositionX = startPositionX - (pointSize * 2)
            }

            //add random point on the screen
            addPoint()

            //start moving
            moveSnake()
        }

        private fun addPoint() {
            val surfaceWidth = surfaceView.width - (pointSize * 2)
            val surfaceHeight = surfaceView.height - (pointSize * 2)

            var randomXPosition = Random().nextInt(surfaceWidth / pointSize)
            var randomYPosition = Random().nextInt(surfaceHeight / pointSize)

            if ((randomXPosition % 2) != 0) {
                randomXPosition += 1
            }

            if ((randomYPosition % 2) != 0) {
                randomYPosition += 1
            }

            positionX = (pointSize * randomXPosition) + pointSize
            positionY = (pointSize * randomXPosition) + pointSize
        }

        private fun moveSnake() {

            timer = Timer()
            timer.scheduleAtFixedRate(object: TimerTask() {
                override public fun run() {
                    var headPositionX = snakePointsList.get(0).getPositionX()
                    var headPositionY = snakePointsList.get(0).getPositionY()

                    //Grow snake, achieve point
                    if (headPositionX == positionX && headPositionY == positionY) {
                        growSnake()
                        addPoint()
                    }

                    when (movingPosition) {
                        "right" -> {
                            snakePointsList.get(0).setPositionX((headPositionX + (pointSize * 2)))
                            snakePointsList.get(0).setPositionY(headPositionY)
                        }
                        "left" -> {
                            snakePointsList.get(0).setPositionX((headPositionX - (pointSize * 2)))
                            snakePointsList.get(0).setPositionY(headPositionY)
                        }
                        "up" -> {
                            snakePointsList.get(0).setPositionX(headPositionX)
                            snakePointsList.get(0).setPositionY((headPositionY - (pointSize * 2)))
                        }
                        "down" -> {
                            snakePointsList.get(0).setPositionX(headPositionX)
                            snakePointsList.get(0).setPositionY((headPositionY + (pointSize * 2)))
                        }
                    }

                    //Check for crash
                    if(checkGameOver(headPositionX, headPositionY)) {
                        timer.purge()
                        timer.cancel()

                        val builder = AlertDialog.Builder(requireActivity())
                        builder.setTitle("Game Over")
                        builder.setMessage("Your score:" + score)
                        if (bestScorevalue < score) {
                            bestScorevalue = score
                            bestScore.setText("Best Score: " + score)
                        }
                        builder.setPositiveButton("Start Again", DialogInterface.OnClickListener { dialogInterface, i ->
                            init()
                        })

                        requireActivity().runOnUiThread(Runnable {
                            builder.show()
                        })
                    } else {
                        var canvas: Canvas? = null
                        try {
                            canvas = surfaceHolder.lockCanvas()
                            if (canvas != null) {
                                canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR)

                                canvas.drawCircle(
                                    snakePointsList[0].getPositionX().toFloat(),
                                    snakePointsList[0].getPositionY().toFloat(),
                                    pointSize.toFloat(),
                                    createPointColor()
                                )

                                canvas.drawCircle(
                                    positionX.toFloat(),
                                    positionY.toFloat(),
                                    pointSize.toFloat(),
                                    createPointColor()
                                )

                                for (i in 1 until snakePointsList.size) {
                                    var getTempPositionX = snakePointsList[i].getPositionX()
                                    var getTempPositionY = snakePointsList[i].getPositionY()

                                    snakePointsList[i].setPositionX(headPositionX)
                                    snakePointsList[i].setPositionY(headPositionY)
                                    canvas.drawCircle(
                                        snakePointsList[i].getPositionX().toFloat(),
                                        snakePointsList[i].getPositionY().toFloat(),
                                        pointSize.toFloat(),
                                        createPointColor()
                                    )

                                    headPositionX = getTempPositionX
                                    headPositionY = getTempPositionY
                                }
                            }
                        } finally {
                            if (canvas != null) {
                                surfaceHolder.unlockCanvasAndPost(canvas)
                            }
                        }
                    }
                }
            }, (1000 - snakeMovingSpeed).toLong(), (1000 - snakeMovingSpeed).toLong())
        }

        private fun growSnake() {
            if (this@freeFragment.isAdded && this@freeFragment.isVisible) {
                var snakePoints = SnakePoints(0, 0)
                (snakePointsList as ArrayList).add(snakePoints)

                score += 1

                requireActivity().runOnUiThread {
                    currentScore.text = score.toString()
                }
            }
        }

        private fun checkGameOver(headPositionX: Int, headPositionY: Int): Boolean {
            var gameOver = false

            if (snakePointsList.get(0).getPositionX() < 0 ||
                    snakePointsList.get(0).getPositionY() < 0 ||
                    snakePointsList.get(0).getPositionX() >= surfaceView.width ||
                    snakePointsList.get(0).getPositionY() >= surfaceView.height) {
                gameOver = true
            } else {
                for (i in 0 until snakePointsList.size) {
                    if (headPositionX == snakePointsList.get(i).getPositionX() &&
                            headPositionY == snakePointsList.get(i).getPositionY()) {
                        gameOver = true
                        break
                    }
                }
            }
            return gameOver
        }

        private fun createPointColor(): Paint {

            if (pointColor == null){
                pointColor = Paint()
                pointColor!!.setColor(snakeColor)
                pointColor!!.isAntiAlias = true
            }

            return pointColor!!

        }
    }
}
