package lesson11.task1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

internal class ComplexTest {

    private fun assertApproxEquals(expected: Complex, actual: Complex, eps: Double) {
        assertEquals(expected.re, actual.re, eps)
        assertEquals(expected.im, actual.im, eps)
    }

    @Test
    @Tag("2")
    fun plus() {
        assertApproxEquals(Complex("4-2i"), Complex("1+2i") + Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex(0.0), Complex("0+0i") + Complex("0+0i"), 1e-10)
        assertApproxEquals(Complex("4.6-3.5i"), Complex("1.6+2i") + Complex("3-5.5i"), 1e-10)
        assertApproxEquals(Complex("1.61+67.6i"), Complex("1.61+2i") + Complex("0+65.6i"), 1e-10)
        assertApproxEquals(Complex("8+2.64i"), Complex("5+2.64i") + Complex("3+0i"), 1e-10)
        assertApproxEquals(Complex("85.5-93.55i"), Complex("100-45i") + Complex("-14.5-48.55i"), 1e-10)
        assertApproxEquals(Complex("-114.5-93.55i"), Complex("-100-45i") + Complex("-14.5-48.55i"), 1e-10)
    }

    @Test
    @Tag("2")
    operator fun unaryMinus() {
        assertApproxEquals(Complex(1.0, -2.0), -Complex(-1.0, 2.0), 1e-10)
        assertApproxEquals(Complex(5.7, -2.0), -Complex(-5.7, 2.0), 1e-10)
        assertApproxEquals(Complex(0.0, -2126.99), -Complex(0.0, 2126.99), 1e-10)
        assertApproxEquals(Complex(16.0, 0.0), -Complex(-16.0, 0.0), 1e-10)
        assertApproxEquals(Complex(0.0, 0.0), -Complex(0.0, 0.0), 1e-10)
    }

    @Test
    @Tag("2")
    fun minus() {
        assertApproxEquals(Complex("2-6i"), Complex("3-4i") - Complex("1+2i"), 1e-10)
        assertApproxEquals(Complex("-7.9-6i"), Complex("8.6-4i") - Complex("16.5+2i"), 1e-10)
        assertApproxEquals(Complex(-0.7), Complex("0.5+0i") - Complex("1.2+0i"), 1e-10)
        assertApproxEquals(Complex(0.0), Complex("0+0i") - Complex("0+0i"), 1e-10)
        assertApproxEquals(Complex("0-160i"), Complex("0-4i") - Complex("0+156i"), 1e-10)
        assertThrows(IllegalArgumentException::class.java) { Complex("-7-i").minus(Complex("4+45i")) }
    }

    @Test
    @Tag("4")
    fun times() {
        assertApproxEquals(Complex("11+2i"), Complex("1+2i") * Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex(0.0), Complex("0+0i") * Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex("0+9.49i"), Complex("0+2.6i") * Complex("3.65+0i"), 1e-10)
        assertApproxEquals(Complex("220.6+329.49i"), Complex("64+2.6i") * Complex("3.65+5i"), 1e-10)
        assertApproxEquals(Complex("0+0i"), Complex("0+0i") * Complex("0+0i"), 1e-10)
        assertThrows(IllegalArgumentException::class.java) { Complex("64+i").times(Complex("5.65+4i")) }
        assertThrows(IllegalArgumentException::class.java) { Complex("*i").times(Complex("0+87i")) }
    }

    @Test
    @Tag("4")
    fun div() {
        assertApproxEquals(Complex("1+2i"), Complex("11+2i") / Complex("3-4i"), 1e-10)
        assertApproxEquals(Complex(3.66666666666), Complex("11+0i") / Complex("3+0i"), 1e-10)
        assertApproxEquals(Complex(Double.NaN, Double.NaN), Complex("0+0i") / Complex("0+0i"), 1e-10)
        assertThrows(IllegalArgumentException::class.java) { Complex("0+i").div(Complex("45.65+4i")) }
        assertThrows(IllegalArgumentException::class.java) { Complex("*-5.5i").div(Complex("0+4i")) }
    }

    @Test
    @Tag("2")
    fun equals() {
        assertApproxEquals(Complex(1.0, 2.0), Complex("1+2i"), 1e-12)
        assertApproxEquals(Complex(1.0, 0.0), Complex(1.0), 1e-12)
        assertApproxEquals(Complex(0.0, 4.0), Complex("0+4i"), 1e-12)
        assertApproxEquals(Complex(9.7, 4.8), Complex("9.7+4.8i"), 1e-12)
        assertApproxEquals(Complex(0.0, 0.0), Complex("0+0i"), 1e-12)
        assertApproxEquals(Complex(15.9, 0.0), Complex("15.9+0i"), 1e-12)
        assertApproxEquals(Complex(15.0, 9.0), Complex("15+9i"), 1e-12)
    }

    @Test
    fun testToString() {
        assertEquals("1+2i", Complex("1+2i").toString())
        assertEquals("3-4i", Complex("3-4i").toString())
        assertEquals("7+0i", Complex("7+0i").toString())
        assertEquals("155-89i", Complex("155-89i").toString())
        assertEquals("7+5i", Complex(7.0, 5.0).toString())
        assertEquals("17.8-8.4i", Complex(17.8, -8.4).toString())
        assertEquals("36+0i", Complex(36.0, 0.0).toString())
        assertEquals("0+0i", Complex(0.0, 0.0).toString())
        assertEquals("36.5+0i", Complex(36.5, 0.0).toString())
    }
}