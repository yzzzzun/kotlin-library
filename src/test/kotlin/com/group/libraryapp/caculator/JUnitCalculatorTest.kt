package com.group.libraryapp.caculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class JUnitCalculatorTest {

    @Test
    fun addTest() {
        //given
        val calculator = Calculator(5)
        //when
        calculator.add(5)
        //then
        assertThat(calculator.number).isEqualTo(10)
    }

    @Test
    fun minusTest() {
        //given
        val calculator = Calculator(5)
        //when
        calculator.minus(5)
        //then
        assertThat(calculator.number).isEqualTo(0)
    }

    @Test
    fun multiplyTest() {
        //given
        val calculator = Calculator(5)
        //when
        calculator.multiply(5)
        //then
        assertThat(calculator.number).isEqualTo(25)
    }

    @Test
    fun divideTest() {
        //given
        val calculator = Calculator(5)
        //when
        calculator.divide(2)
        //then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun divideZeroTest() {
        //given
        val calculator = Calculator(5)
        val message = assertThrows<IllegalArgumentException> { calculator.divide(0) }
            .message
        assertThat(message).isEqualTo("0으로 나눌 수 없습니다")
    }
}
