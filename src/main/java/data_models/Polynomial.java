package data_models;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.Math.abs;

public class Polynomial {
    private Map<Integer, Monomial> polynom_terms;

    public Polynomial() {
        polynom_terms = new TreeMap<>(Comparator.reverseOrder());//for order
    }


    public void addMonomial(Monomial monom) {
        polynom_terms.put(monom.getPower(), monom);//we use the power as the key
    }

    public Map<Integer, Monomial> getTerms() {
        return polynom_terms;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#.###");
        boolean first = true;
        StringBuilder polynom_String = new StringBuilder();
        boolean allZero = true;
        for (int power : polynom_terms.keySet()) {
            Monomial monomial = polynom_terms.get(power);
            double coefficient = monomial.getCoefficient();
            if (coefficient != 0) {
                allZero = false;
                break;
            }
        }
        if (allZero) {
            return "0";
        }

        for (int power : polynom_terms.keySet()) {
            Monomial monomial = polynom_terms.get(power);
            double coefficient = monomial.getCoefficient();
            if (coefficient == 0) {
                continue;
            }

            if (first) {//this is to make sure that if the first element is positive we wont have +, but if is negative we have -
                if (coefficient < 0) {
                    polynom_String.append("-");
                    coefficient = -coefficient;
                }
                first = false;
            } else {
                if (coefficient >0) {
                    polynom_String.append("+");
                } else if (coefficient < 0) {
                    polynom_String.append("-");
                    coefficient = -coefficient;
                }
                first = false;
            }

            if (coefficient != 0 || power == 0) {
                if (coefficient != 1 && coefficient != -1 || power == 0) {//to make sure we don't have 1x^ but only x^
                    coefficient = Double.parseDouble(df.format(coefficient));
                    if (coefficient == (int) coefficient) {//to not have 2.0 but only 2
                        polynom_String.append((int) coefficient);
                    } else {
                        polynom_String.append(abs(coefficient));
                    }
                }
            }
            if (power > 0) {
                polynom_String.append("x");
            }
            if (power > 1) {
                polynom_String.append("^").append(power);
            }
            if(coefficient==0 && power==0)
            {
                polynom_String.append((int) coefficient);
            }

        }
        return polynom_String.toString();
    }

    public Monomial getFirstTerm() {
        Monomial firstElement = null;
        for (Monomial monomial : polynom_terms.values()) {
            if (firstElement==null || monomial.getPower() > firstElement.getPower()) {
                firstElement = monomial;
            }
        }
        return firstElement;
    }

    public int getDegree() {
        Monomial monomial = getFirstTerm();
        if (monomial == null) {
            return 0;
        } else {
            return monomial.getPower();
        }
    }

}


