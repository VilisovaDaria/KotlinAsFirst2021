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
    }

    @Test
    @Tag("2")
    operator fun unaryMinus() {
        assertApproxEquals(Complex(1.0, -2.0), -Complex(-1.0, 2.0), 1e-10)
    }

    @Test
    @Tag("2")
    fun minus() {
        assertApproxEquals(Complex("2-6i"), Complex("3-4i") - Complex("1+2i"), 1e-10)
    }

    @Test
    @Tag("4")
    fun times() {
        assertApproxEquals(Complex("11+2i"), Complex("1+2i") * Complex("3-4i"), 1e-10)
    }

    @Test
    @Tag("4")
    fun div() {
        assertApproxEquals(Complex("1+2i"), Complex("11+2i") / Complex("3-4i"), 1e-10)
    }

    @Test
    @Tag("2")
    fun equals() {
        assertApproxEquals(Complex(1.0, 2.0), Complex("1+2i"), 1e-12)
        assertApproxEquals(Complex(1.0, 0.0), Complex(1.0), 1e-12)
        assertApproxEquals(Complex(9.0, 4.0), Complex("9+4i"), 1e-12)
    }

    @Test
    fun testToString() {
        assertEquals("1+2i", Complex("1+2i").toString())
        assertEquals("3-4i", Complex("3-4i").toString())
        assertEquals("7+0i", Complex("7+0i").toString())
        assertEquals("155-89i", Complex("155-89i").toString())
        assertEquals("7+5i", Complex(7.0, 5.0).toString())
        assertEquals("17-8i", Complex(17.0, -8.0).toString())
        assertEquals("36+0i", Complex(36.0, 0.0).toString())
        assertEquals("0+0i", Complex(0.0, 0.0).toString())
        assertEquals("36.5+0i", Complex(36.5, 0.0).toString())
    }
}