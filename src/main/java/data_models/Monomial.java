package data_models;

public class Monomial {
    private int power;
    private double coefficient;


    public Monomial(double coefficient,int power)
    {
        this.coefficient=coefficient;
        this.power=power;
    }
    public  double getCoefficient()
    {return coefficient;
    }

    public int getPower() {
        return power;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public void setPower(int power) {
        this.power = power;
    }

}
