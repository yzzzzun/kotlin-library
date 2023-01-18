package com.group.libraryapp.caculator

import org.junit.jupiter.api.Assertions.*

fun main(){
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest()
    calculatorTest.minusTest()
    calculatorTest.multiplyTest()
    calculatorTest.divideTest()
    calculatorTest.divideZeroTest()
}


class CalculatorTest {
    fun addTest(){
        //given
        val calculator = Calculator(5)

        //when
        calculator.add(3)

        //then
        if(calculator.number != 8){
            throw IllegalStateException()
        }
    }

    fun minusTest(){
        //given
        val calculator = Calculator(5)

        //when
        calculator.minus(3)

        //then
        if(calculator.number != 2){
            throw IllegalStateException()
        }
    }

    fun multiplyTest(){
        //given
        val calculator = Calculator(5)

        //when
        calculator.multiply(3)

        //then
        if(calculator.number != 15){
            throw IllegalStateException()
        }
    }

    fun divideTest(){
        val calculator = Calculator(5)

        //when
        calculator.divide(5)

        //then
        if(calculator.number != 1){
            throw IllegalStateException()
        }
    }

    fun divideZeroTest(){
        val calculator = Calculator(5)

        //when
        try{
            calculator.divide(0)
        }catch (e : IllegalArgumentException){
            if(e.message != "0으로 나눌 수 없습니다"){
                throw IllegalStateException()
            }
            return
        }catch(e:Exception){
            throw IllegalStateException()
        }
        throw IllegalStateException("기대하지 않은 예외")
    }

}
