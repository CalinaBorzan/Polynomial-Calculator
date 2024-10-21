import data_models.Monomial;
import data_models.Polynomial;
import logic.PolynomialParserVerification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PolynomialParserTest {

    @Test
    void testValidPolynomialParsing() {
        PolynomialParserVerification parser = new PolynomialParserVerification();
        parser.parsing("3x^2+2x^1-5");
        Polynomial result = parser;

        assertEquals(3, result.getTerms().get(2).getCoefficient(), "Coefficient of x^2 should be 3");
        assertEquals(2, result.getTerms().get(1).getCoefficient(), "Coefficient of x should be 2");
        assertEquals(-5, result.getTerms().get(0).getCoefficient(), "Constant term should be -5");
    }

    @Test
    void testParsingWithMissingCoefficients() {
        PolynomialParserVerification parser = new PolynomialParserVerification();
        parser.parsing("x^2+x^1");
        Polynomial result = parser;

        assertEquals(1, result.getTerms().get(2).getCoefficient(), "Coefficient of x^2 should be 1");
        assertEquals(1, result.getTerms().get(1).getCoefficient(), "Coefficient of x should be 1");
    }

    @Test
    void testParsingWithMissingPowers() {
        PolynomialParserVerification parser = new PolynomialParserVerification();
        parser.parsing("3x^1+2");
        Polynomial result = parser;

        assertEquals(3, result.getTerms().get(1).getCoefficient(), "Coefficient of x should be 3");
        assertEquals(2, result.getTerms().get(0).getCoefficient(), "Constant term should be 2");
    }

    @Test
    void testParsingInvalidFormat() {
        PolynomialParserVerification parser = new PolynomialParserVerification();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parsing("3x^2.5+2x^1-5");
        }, "Expected IllegalArgumentException for invalid polynomial string format.");

        assertEquals("Invalid polynomial string format", exception.getMessage(), "Exception message mismatch.");
    }

    @Test
    void testParsingEmptyString() {
        PolynomialParserVerification parser = new PolynomialParserVerification();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parsing("");
        }, "Expected IllegalArgumentException for empty polynomial string.");

        assertEquals("Invalid polynomial string format", exception.getMessage(), "Exception message mismatch.");
    }
    @Test
    void testNegativeCoefficients() {
        PolynomialParserVerification parser = new PolynomialParserVerification();
        parser.parsing("-3x^2+2x^1-1");
        Polynomial result = parser;

        assertEquals(-3, result.getTerms().get(2).getCoefficient(), "Coefficient of x^2 should be -3");
        assertEquals(2, result.getTerms().get(1).getCoefficient(), "Coefficient of x should be 2");
        assertEquals(-1, result.getTerms().get(0).getCoefficient(), "Constant term should be -1");
    }

    @Test
    void testZeroCoefficientTerms() {
        PolynomialParserVerification parser = new PolynomialParserVerification();
        parser.parsing("0x^3+3x^2");
        Polynomial result = parser;

        assertEquals(3, result.getTerms().get(2).getCoefficient(), "Coefficient of x^2 should be 3");
    }

    @Test
    void testInvalidCharacters() {
        PolynomialParserVerification parser = new PolynomialParserVerification();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parsing("3x^2+y^2");
        }, "Expected IllegalArgumentException for invalid characters in polynomial string.");

        assertEquals("Invalid polynomial string format", exception.getMessage(), "Exception message mismatch.");
    }

}
