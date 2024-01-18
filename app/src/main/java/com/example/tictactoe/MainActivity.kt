package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private var currentPlayer = 'X'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = Array(3) { row ->
            Array(3) { col ->
                findViewById<Button>(resources.getIdentifier("button$row$col", "id", packageName)).apply {
                    setOnClickListener { onButtonClick(this, row, col) }
                }
            }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener { resetGame() }
    }

    private fun onButtonClick(button: Button, row: Int, col: Int) {
        if (button.text.isEmpty()) {
            button.text = currentPlayer.toString()
            if (checkWinner(row, col)) {
                showToast("Player $currentPlayer wins!")
                resetGame()
            } else if (isBoardFull()) {
                showToast("It's a tie!")
                resetGame()
            } else {
                currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            }
        } else {
            showToast("Invalid move. Cell already taken. Try again.")
        }
    }

    private fun checkWinner(row: Int, col: Int): Boolean {
        // Check row
        if (buttons[row][0].text == currentPlayer.toString() &&
            buttons[row][1].text == currentPlayer.toString() &&
            buttons[row][2].text == currentPlayer.toString()
        ) {
            return true
        }

        // Check column
        if (buttons[0][col].text == currentPlayer.toString() &&
            buttons[1][col].text == currentPlayer.toString() &&
            buttons[2][col].text == currentPlayer.toString()
        ) {
            return true
        }

        // Check diagonals
        if (row == col || row + col == 2) {
            if (buttons[0][0].text == currentPlayer.toString() &&
                buttons[1][1].text == currentPlayer.toString() &&
                buttons[2][2].text == currentPlayer.toString()
            ) {
                return true
            }
            if (buttons[0][2].text == currentPlayer.toString() &&
                buttons[1][1].text == currentPlayer.toString() &&
                buttons[2][0].text == currentPlayer.toString()
            ) {
                return true
            }
        }

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in buttons) {
            for (button in row) {
                if (button.text.isEmpty()) {
                    return false
                }
            }
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun resetGame() {
        for (row in buttons) {
            for (button in row) {
                button.text = ""
            }
        }
        currentPlayer = 'X'
    }
}
