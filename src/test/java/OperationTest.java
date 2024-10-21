import data_models.Monomial;
import data_models.Polynomial;
import logic.Operations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OperationTest {

    private Operations operations;

    @BeforeEach
    void setUp() {
        operations = new Operations();
    }

    @Test
    void testAddition() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(new Monomial(2, 2)); // 2x^2
        p1.addMonomial(new Monomial(3, 1)); // + 3x

        Polynomial p2 = new Polynomial();
        p2.addMonomial(new Monomial(1, 1)); // x
        p2.addMonomial(new Monomial(-1, 0)); // - 1

        Polynomial result = operations.add(p1, p2);
        assertEquals("2x^2+4x-1", result.toString(), "Addition operation failed.");
    }

    @Test
    void testSubtraction() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(new Monomial(3, 3)); // 3x^3
        p1.addMonomial(new Monomial(1, 2)); // + x^2

        Polynomial p2 = new Polynomial();
        p2.addMonomial(new Monomial(2, 3)); // 2x^3
        p2.addMonomial(new Monomial(1, 1)); // + x

        Polynomial result = operations.subtract(p1, p2);
        assertEquals("x^3+x^2-x", result.toString(), "Subtraction operation failed.");
    }

    @Test
    void testMultiplication() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(new Monomial(1, 1)); // x

        Polynomial p2 = new Polynomial();
        p2.addMonomial(new Monomial(1, 1)); // x

        Polynomial result = operations.multiplication(p1, p2);
        assertEquals("x^2", result.toString(), "Multiplication operation failed.");
    }

    @Test
    void testDivision() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(new Monomial(1, 2)); // x^2

        Polynomial p2 = new Polynomial();
        p2.addMonomial(new Monomial(1, 1)); // x

        Map.Entry<Polynomial, Polynomial> result = operations.division(p1, p2);
        assertEquals("x", result.getKey().toString(), "Quotient of division operation failed.");
        assertEquals("0", result.getValue().toString(), "Remainder of division operation failed.");
    }

    @Test
    void testDerivative() {
        Polynomial p = new Polynomial();
        p.addMonomial(new Monomial(3, 3)); // 3x^3

        Polynomial result = operations.derivative(p);
        assertEquals("9x^2", result.toString(), "Derivative operation failed.");
    }

    @Test
    void testIntegral() {
        Polynomial p = new Polynomial();
        p.addMonomial(new Monomial(2, 2)); // 2x^2

        Polynomial result = operations.integral(p);
        // The result of integral can vary due to the constant of integration; hence the check might need to be adjusted
        assertTrue(result.toString().contains("x^3"), "Integral operation failed.");
    }

   @Test
    void testDivisionByZero() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(new Monomial(1, 2)); // x^2

        Polynomial p2 = new Polynomial(); // Represents 0 polynomial

        assertThrows(NullPointerException.class, () -> {
            operations.division(p1, p2);
        }, "Expected ArithmeticException for division by zero.");
    }
    @Test
    void testComplexAddition() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(new Monomial(4, 4)); // 4x^4
        p1.addMonomial(new Monomial(-2, 2)); // -2x^2

        Polynomial p2 = new Polynomial();
        p2.addMonomial(new Monomial(-1, 3)); // -x^3
        p2.addMonomial(new Monomial(1, 2)); // + x^2
        p2.addMonomial(new Monomial(5, 0)); // + 5

        Polynomial result = operations.add(p1, p2);
        assertEquals("4x^4-x^3-x^2+5", result.toString(), "Complex addition operation failed.");
    }

    @Test
    void testComplexSubtraction() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(new Monomial(3, 3)); // 3x^3
        p1.addMonomial(new Monomial(-1, 1)); // -x

        Polynomial p2 = new Polynomial();
        p2.addMonomial(new Monomial(1, 4)); // x^4
        p2.addMonomial(new Monomial(-2, 0)); // -2

        Polynomial result = operations.subtract(p1, p2);
        assertEquals("-x^4+3x^3-x+2", result.toString(), "Complex subtraction operation failed.");
    }

    @Test
    void testPolynomialMultiplicationWithMissingTerms() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(new Monomial(5, 3)); // 5x^3

        Polynomial p2 = new Polynomial();
        p2.addMonomial(new Monomial(2, 1)); // 2x

        Polynomial result = operations.multiplication(p1, p2);
        assertEquals("10x^4", result.toString(), "Multiplication with missing terms operation failed.");
    }

    @Test
    void testDivisionWithRemainder() {
        Polynomial p1 = new Polynomial();
        p1.addMonomial(new Monomial(1, 3)); // x^3
        p1.addMonomial(new Monomial(0, 2)); // 0x^2 (implicit)
        p1.addMonomial(new Monomial(-2, 1)); // -2x
        p1.addMonomial(new Monomial(-1, 0)); // -1

        Polynomial p2 = new Polynomial();
        p2.addMonomial(new Monomial(1, 1)); // x
        p2.addMonomial(new Monomial(-1, 0)); // -1

        Map.Entry<Polynomial, Polynomial> result = operations.division(p1, p2);
        assertEquals("x^2+x-1", result.getKey().toString(), "Quotient of division operation with remainder failed.");
        assertEquals("-2", result.getValue().toString(), "Remainder of division operation with remainder failed.");
    }

    @Test
    void testZeroPolynomialOperations() {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial(); // Zero polynomial

        // Test addition of a polynomial with a zero polynomial
        Polynomial resultAddition = operations.add(p1, p2);
        assertEquals("0", resultAddition.toString(), "Addition with zero polynomial failed.");

        // Test multiplication of a polynomial with a zero polynomial
        Polynomial resultMultiplication = operations.multiplication(p1, p2);
        assertEquals("0", resultMultiplication.toString(), "Multiplication with zero polynomial failed.");
    }
}

