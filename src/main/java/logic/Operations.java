package logic;

import data_models.Monomial;
import data_models.Polynomial;

import java.util.AbstractMap;
import java.util.Map;

public class Operations {

    public static Polynomial add(Polynomial param1, Polynomial param2) {
        Polynomial add = new Polynomial();

        for (Monomial monomial : param1.getTerms().values()) {
            int power = monomial.getPower();
            double coefficient = monomial.getCoefficient();
            add.addMonomial(new Monomial(coefficient, power));
        }

        for (Monomial monomial : param2.getTerms().values()) {
            int power = monomial.getPower();
            double coefficient = monomial.getCoefficient();
            if (add.getTerms().containsKey(power)) {
                coefficient += add.getTerms().get(power).getCoefficient();
            }
            add.addMonomial(new Monomial(coefficient, power));
        }
        return add;
    }

    public static Polynomial subtract(Polynomial param1, Polynomial param2) {
        Polynomial sub = new Polynomial();

        for (Monomial monomial : param1.getTerms().values()) {
            int power = monomial.getPower();
            double coefficient = monomial.getCoefficient();
            sub.addMonomial(new Monomial(coefficient, power));
        }
        for (Monomial monomial : param2.getTerms().values()) {
            int power = monomial.getPower();
            double coefficient = monomial.getCoefficient();
            if (sub.getTerms().containsKey(power)) {
                coefficient -= sub.getTerms().get(power).getCoefficient();
            }
            sub.addMonomial(new Monomial(-coefficient, power));}
        return sub;
    }

    public static Polynomial multiplication(Polynomial param1, Polynomial param2) {
        Polynomial mult = new Polynomial();

        for (Monomial monomial : param1.getTerms().values()) {
            double coefficient_mult = 0;
            int power_mult = 0;
            int power = monomial.getPower();
            double coefficient = monomial.getCoefficient();
            for (Monomial monomial2 : param2.getTerms().values()) {
                int power2 = monomial2.getPower();
                double coefficient2 = monomial2.getCoefficient();
                coefficient_mult = coefficient * coefficient2;
                power_mult = power + power2;
                mult.addMonomial(new Monomial(coefficient_mult, power_mult));
            }
        }
        return mult;
    }


    public static Map.Entry<Polynomial, Polynomial> division(Polynomial param1, Polynomial param2) {
        if (param2.getDegree() == 0 && param2.getTerms().get(0).getCoefficient() == 0) {
            throw new ArithmeticException("Division by zero");
        }
        Polynomial q = new Polynomial();
        Polynomial te = new Polynomial();
        Polynomial r = new Polynomial();
        r.getTerms().putAll(param1.getTerms());

        while (!r.getTerms().isEmpty() && r.getDegree() >= param2.getDegree()) {
            Monomial firstR = r.getFirstTerm();
            Monomial firstP2 = param2.getFirstTerm();
            double coefficientT = firstR.getCoefficient() / firstP2.getCoefficient();
            int powerT = firstR.getPower() - firstP2.getPower();
            Monomial t = new Monomial(coefficientT, powerT);
            q.addMonomial(t);

            te.getTerms().clear();
            for (Monomial monomial2 : param2.getTerms().values()) {
                double coefficientL = t.getCoefficient() * monomial2.getCoefficient();
                int powerL = t.getPower() + monomial2.getPower();
                Monomial l = new Monomial(coefficientL, powerL);
                te.addMonomial(l);
            }
            r = Operations.subtract(r, te);
            while (r.getTerms().containsKey(r.getDegree()) && r.getTerms().get(r.getDegree()).getCoefficient() == 0) {
                r.getTerms().remove(r.getDegree());
            }
        }
        Map.Entry<Polynomial, Polynomial> result = new AbstractMap.SimpleEntry<>(q, r);
        return result;
    }

    public static Polynomial derivative(Polynomial param1) {
        Polynomial derivative = new Polynomial();
        for (Monomial monomial : param1.getTerms().values()) {
            double coefficient_derivative = 0;
            int power_derivative = 0;
            int power = monomial.getPower();
            double coefficient = monomial.getCoefficient();
            coefficient_derivative = coefficient * power;
            power_derivative = power-1;
            derivative.addMonomial(new Monomial(coefficient_derivative, power_derivative));
        }
        return derivative;
    }
    public static Polynomial integral(Polynomial param1) {
        Polynomial integral = new Polynomial();

        for (Monomial monomial : param1.getTerms().values()) {
            double coefficient_integral = 0;
            int power_integral= 0;
            int power = monomial.getPower();
            double coefficient = monomial.getCoefficient();
            coefficient_integral = coefficient/(power+1);
            power_integral = power+1;
            integral.addMonomial(new Monomial(coefficient_integral, power_integral));
        }
        return integral;
    }
}


