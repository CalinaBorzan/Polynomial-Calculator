package logic;

import data_models.Monomial;
import data_models.Polynomial;

public class PolynomialParserVerification extends Polynomial {
    public void parsing(String polynom_terms) {
        if (!verifyPolynomial(polynom_terms)) {
            throw new IllegalArgumentException("Invalid polynomial string format");
        } else {
            String[] termString = polynom_terms.split("(?=[-+])");//split term by term
            for (String ter : termString) {

                if (ter.isEmpty()) {
                    continue;
                }
                if (!ter.matches(".*\\d+.*") && !ter.contains("x")) {
                    throw new IllegalArgumentException("Invalid polynomial string format");
                }
                String[] term;
                if (ter.contains("x") && !ter.contains("^")) {
                    term = new String[]{"1", "1"};
                } else {
                    term = ter.split("x\\^");
                }
                double coefficient = 0.0;
                int power = 0;
                if (term[0].isEmpty()) {
                    coefficient = 1.0;
                } else if (term[0].equals("+")) {
                    coefficient = 1.0;
                } else if (term[0].equals("-")) {
                    coefficient = -1.0;
                } else {
                    coefficient = Double.parseDouble(term[0].trim());
                }
                if (term.length > 1) {
                    power = Integer.parseInt(term[1].trim());
                } else if (term[0].contains("x")) {
                    power = 1;
                }
                boolean termExists = false;
                for (Monomial monomial : getTerms().values()) {
                    if (monomial.getPower() == power) {
                        monomial.setCoefficient(monomial.getCoefficient() + coefficient);
                        termExists = true;
                        break;
                    }
                }
                if (!termExists) {
                    addMonomial(new Monomial(coefficient, power));
                }
            }
        }
    }
    public static boolean verifyPolynomial(String polynomialString) {
        String[] terms = polynomialString.split("(?=[-+])");
        if (polynomialString.isEmpty() || terms[terms.length - 1].isEmpty()) {
            return false;
        }
        boolean signFound = false;
        for (String term : terms) {
            if (term.isEmpty()) {
                return false;
            }
            if (!isValidTerm(term)) {
                return false;
            }
            if (term.equals("+") || term.equals("-")) {
                if (signFound) {
                    return false;
                }
                signFound = true;
            } else {
                signFound = false;
            }
        }
        String lastTerm = terms[terms.length - 1];
        return lastTerm.matches("^[+-]?(?:\\d*\\.?\\d*x?(?:\\^\\d+)?)$");
    }


    private static boolean isValidTerm(String term) {
        String regex = "^([+-]?\\d*\\.?\\d*x?(\\^\\d+)?)$";
        return term.matches(regex) && !term.isEmpty();
    }

}
