package com.heewoong.threebasicscreens.ui.free

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
    private lateinit var startButton: Button

    private lateinit var surfaceHolder: SurfaceHolder
    private var bestScorevalue: Int = 0

    private var snakePointsList: List<SnakePoints> = ArrayList<SnakePoints>()
    private var movingPosition: String = "right"
    private var score: Int = 0
    private var positionX: Int = 0
    private var positionY: Int = 0

    private val pointSize: Int = 30
    private val defaultTalePoints = 3
    private val snakeColor = Color.GREEN
    private val snakeMovingSpeed = 800
    private var timer: Timer = Timer()

    private var pointColor: Paint? = null

    private var colorArray: ArrayList<Int> = arrayListOf(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_free, container, false)

        surfaceView = view.findViewById(R.id.surfaceView)
        currentScore = view.findViewById(R.id.current_score)
        bestScore = view.findViewById(R.id.best_score)
        startButton = view.findViewById(R.id.startButton)

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
    override fun onDetach() {
        super.onDetach()
        timer.cancel()
        timer.purge()
        startButton.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        startButton.visibility = View.VISIBLE
    }

    inner class SurfaceCallback : SurfaceHolder.Callback {
        @SuppressLint("SetTextI18n")
        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            // Surface의 상태가 변경되었을 때 호출되는 메서드
            bestScorevalue = getBestScore()
            bestScore.text = "Best Score: $bestScorevalue"
            surfaceHolder = holder
            startButton.setOnClickListener {
                startgame()
                startButton.visibility = View.GONE
            }
        }

        fun startgame() {init()}

        override fun surfaceCreated(holder: SurfaceHolder) {
            // Surface가 처음으로 생성되었을 때 호출되는 메서드
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
            // Surface가 소멸되었을 때 호출되는 메서드
        }
        private fun getBestScore(): Int {
            val sharedPreferences = requireActivity().getSharedPreferences("best",Context.MODE_PRIVATE)
            return sharedPreferences.getInt("best_score", 0)
        }

        private fun saveBestScore(score: Int) {
            val sharedPreferences = requireActivity().getSharedPreferences("best", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("best_score", score)
            editor.apply()
        }
        @SuppressLint("SetTextI18n")
        private fun init() {

            startButton.visibility = View.GONE
            bestScorevalue = getBestScore()
            bestScore.text = "Best Score: $bestScorevalue"

            //clear snake points
            snakePointsList = ArrayList<SnakePoints>()
            currentScore.setText("0")
            score = 0
            movingPosition = "right"

            //default starting position
            var startPositionX = (pointSize) * defaultTalePoints

            for (i in 0..defaultTalePoints) {
                val snakePoint = SnakePoints(startPositionX, pointSize)
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
            pointColor = createFoodColor()
            positionX = (pointSize * randomXPosition) + pointSize
            positionY = (pointSize * randomYPosition) + pointSize


        }

        private fun moveSnake() {

            timer = Timer()
            timer.scheduleAtFixedRate(object: TimerTask() {
                @SuppressLint("SetTextI18n")
                override fun run() {

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
                            bestScore.setText("Best Score: $score")
                            saveBestScore(score)
                        }
                        builder.setPositiveButton("Start Again", { dialogInterface, i ->
                            init()
                        })

                        requireActivity().runOnUiThread({
                            builder.show()
                            startButton.visibility = View.VISIBLE
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
                                    createPointColor(0)
                                )

                                canvas.drawCircle(
                                    positionX.toFloat(),
                                    positionY.toFloat(),
                                    pointSize.toFloat(),
                                    pointColor!!
                                )


                                for (i in 1 until snakePointsList.size-1) {
                                    val getTempPositionX = snakePointsList[i].getPositionX()
                                    val getTempPositionY = snakePointsList[i].getPositionY()

                                    snakePointsList[i].setPositionX(headPositionX)
                                    snakePointsList[i].setPositionY(headPositionY)
                                    canvas.drawCircle(
                                        snakePointsList[i].getPositionX().toFloat(),
                                        snakePointsList[i].getPositionY().toFloat(),
                                        pointSize.toFloat(),
                                        createPointColor(i)
                                    )

                                    headPositionX = getTempPositionX
                                    headPositionY = getTempPositionY
                                }
                                snakePointsList[snakePointsList.size-1].setPositionX(headPositionX)
                                snakePointsList[snakePointsList.size-1].setPositionY(headPositionY)
                                canvas.drawCircle(
                                    snakePointsList[snakePointsList.size-1].getPositionX().toFloat(),
                                    snakePointsList[snakePointsList.size-1].getPositionY().toFloat(),
                                    pointSize.toFloat(),
                                    createPointColor(snakePointsList.size-1))
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
                val snakePoints = SnakePoints(0, 0)
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
                colorArray = arrayListOf(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN)
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

        private fun createPointColor(i: Int): Paint {

            val snakePaint = Paint()
            snakePaint.color = colorArray[i]
            snakePaint.isAntiAlias = true

            return snakePaint


        }
        private fun createFoodColor(): Paint {
            // 랜덤한 음식의 색상을 생성
            val random = Random()
            val r = random.nextInt(256)
            val g = random.nextInt(256)
            val b = random.nextInt(256)

            val randomColor = Color.rgb(r, g, b)

            val paint = Paint()
            paint.color = randomColor
//            Log.d("color", "$randomColor")
            colorArray.add(randomColor)
//            Log.d("color", "$randomColor")
            paint.isAntiAlias = true

            return paint
        }
    }
}