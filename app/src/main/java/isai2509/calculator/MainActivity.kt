package isai2509.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var expressionTextView: MaterialTextView
    private lateinit var resultTextView: MaterialTextView
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    @SuppressLint("ShowToast", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        }

    fun onDigit(view: View){
        expressionTextView = findViewById(R.id.expression)
        if(stateError) {
            expressionTextView.text = (view as MaterialButton).text
            stateError = false
        }
        else {
            expressionTextView.append((view as MaterialButton).text)
        }
        lastNumeric = true


        if(lastNumeric && !stateError){
            expressionTextView = findViewById(R.id.expression)
            resultTextView = findViewById(R.id.result)
            val txt = expressionTextView.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                resultTextView.text  = result.toString()
            }catch (ex: Exception){
                resultTextView.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

    fun onDecimal(view: View) {
        if (lastNumeric && !stateError && !lastDot) {
            expressionTextView = findViewById(R.id.expression)
            expressionTextView.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        if( lastNumeric && !stateError){
            expressionTextView = findViewById(R.id.expression)
            expressionTextView.append((view as MaterialButton).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEqual(view: View){
        if(lastNumeric && !stateError){
            expressionTextView = findViewById(R.id.expression)
            resultTextView = findViewById(R.id.result)
            val txt = expressionTextView.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                expressionTextView.text  = result.toString()
                resultTextView.text = ""
                lastDot = true
            }catch (ex: Exception){
                resultTextView.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

    fun onClear(view: View){
        expressionTextView = findViewById(R.id.expression)
        resultTextView = findViewById(R.id.result)
        this.expressionTextView.text = ""
        this.resultTextView.text = ""
        lastNumeric  = false
        stateError = false
        lastDot = false
    }

    fun onDelete(view: View){
        val str = expressionTextView.text.toString()
        if(lastNumeric && str.isNotEmpty()){
            expressionTextView.text = str.substring(0, str.length-1)
            resultTextView.text = ""
        }
    }

}
