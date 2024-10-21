package gui;


import data_models.Polynomial;
import logic.Operations;
import logic.PolynomialParserVerification;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class Interface extends JFrame implements ActionListener {

    private JPanel mainPanel = new JPanel(new GridBagLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            int width = getWidth();
            int height = getHeight();
            Color color1 = new Color(173, 216, 230); // Baby Blue
            Color color2 = Color.PINK;
            GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
            g2d.dispose();
        }
    };
    private GridBagConstraints gbc = new GridBagConstraints();
    private JButton addButton = new JButton("+");
    private JButton subtractButton = new JButton("-");
    private JButton multiplyButton = new JButton("*");
    private JButton divideButton = new JButton("/");
    private JButton derivativeButton = new JButton("d/dx");
    private JButton integralButton = new JButton("∫dx");
    private JTextField polynomialInput1 = new JTextField(20);
    private JTextField polynomialInput2 = new JTextField(20);
    private JLabel resultLabel = new JLabel("Result:");
    private JLabel operationTextLabel = new JLabel("Choose an operation:");
    private Operations operations = new Operations();

    public Interface(String name) {
        super(name);
        setPreferredSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(new JLabel("First Polynomial:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(polynomialInput1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(new JLabel("Second Polynomial:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(polynomialInput2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(operationTextLabel, gbc);
        operationTextLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(createButtonPanel(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(resultLabel, gbc);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);


        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.FOREGROUND, new GradientPaint(0, 0, Color.PINK, 200, 100, new Color(173, 216, 230))); // Baby Blue
        Font buttonFont = new Font("Arial", Font.BOLD, 14).deriveFont(attributes);


        addButton.setFont(buttonFont);
        subtractButton.setFont(buttonFont);
        multiplyButton.setFont(buttonFont);
        divideButton.setFont(buttonFont);
        derivativeButton.setFont(buttonFont);
        integralButton.setFont(buttonFont);


        addButton.addActionListener(this);
        subtractButton.addActionListener(this);
        multiplyButton.addActionListener(this);
        divideButton.addActionListener(this);
        derivativeButton.addActionListener(this);
        integralButton.addActionListener(this);


        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        gbcButton.insets = new Insets(5, 5, 5, 5);
        gbcButton.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(addButton, gbcButton);

        gbcButton.gridx = 1;
        buttonPanel.add(subtractButton, gbcButton);

        gbcButton.gridx = 2;
        buttonPanel.add(multiplyButton, gbcButton);

        gbcButton.gridx = 0;
        gbcButton.gridy = 1;
        buttonPanel.add(divideButton, gbcButton);

        gbcButton.gridx = 1;
        buttonPanel.add(derivativeButton, gbcButton);

        gbcButton.gridx = 2;
        buttonPanel.add(integralButton, gbcButton);

        return buttonPanel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String polynomialStr1 = polynomialInput1.getText();
        String polynomialStr2 = polynomialInput2.getText();
        PolynomialParserVerification polynomial1 = new PolynomialParserVerification();
        PolynomialParserVerification polynomial2 = new PolynomialParserVerification();


        boolean isDerivativeOrIntegral = e.getSource() == derivativeButton || e.getSource() == integralButton;

        try {
            if (isDerivativeOrIntegral) {

                if (!PolynomialParserVerification.verifyPolynomial(polynomialStr1)) {
                    resultLabel.setText("Error: Invalid polynomial format. Please enter a valid polynomial.");
                    return;
                }
                polynomial1.parsing(polynomialStr1);
            } else {

                if (!PolynomialParserVerification.verifyPolynomial(polynomialStr1) || !PolynomialParserVerification.verifyPolynomial(polynomialStr2)) {
                    resultLabel.setText("Error: Invalid polynomial format. Please enter valid polynomials.");
                    return;
                }
                polynomial1.parsing(polynomialStr1);
                polynomial2.parsing(polynomialStr2);
            }
        } catch (IllegalArgumentException ex) {
            resultLabel.setText("Error: Invalid polynomial. Please enter valid polynomials.");
            return;
        }

        JButton source = (JButton) e.getSource();
        if (isDerivativeOrIntegral) {

            if (source == derivativeButton) {
                Polynomial result = operations.derivative(polynomial1);
                if (result != null) {
                    resultLabel.setText("Derivative Result: " + result.toString());
                } else {
                    resultLabel.setText("Error: Invalid operation. Please try again.");
                }
            } else if (source == integralButton) {
                Polynomial result = operations.integral(polynomial1);
                if (result != null) {
                    resultLabel.setText("Integral Result: " + result.toString());
                } else {
                    resultLabel.setText("Error: Invalid operation. Please try again.");
                }
            }
        } else {

            if (source == divideButton) {
                try {
                    Map.Entry<Polynomial, Polynomial> divisionResult = operations.division(polynomial1, polynomial2);
                    Polynomial quotient = divisionResult.getKey();
                    Polynomial remainder = divisionResult.getValue();
                    resultLabel.setText("Division Result: Quotient: " + quotient.toString() + ", Remainder: " + remainder.toString());
                } catch (ArithmeticException ex) {
                    resultLabel.setText("Error: Division by zero is not allowed.");
                } catch (IllegalArgumentException ex) {
                    resultLabel.setText("Error: Invalid operation. Please try again.");
                }
            } else {

                String operationText = "";
                String resultString = "";
                try {
                    if (source == addButton) {
                        operationText = "Addition";
                        resultString = String.valueOf(operations.add(polynomial1, polynomial2));
                    } else if (source == subtractButton) {
                        operationText = "Subtraction";
                        resultString = String.valueOf(operations.subtract(polynomial1, polynomial2));
                    } else if (source == multiplyButton) {
                        operationText = "Multiplication";
                        resultString = String.valueOf(operations.multiplication(polynomial1, polynomial2));
                    }

                    if (resultString != null && !resultString.isEmpty()) {
                        resultLabel.setText(operationText + " Result: " + resultString);
                    } else {
                        resultLabel.setText("Error: Invalid operation. Please try again.");
                    }
                } catch (Exception ex) {
                    resultLabel.setText("Error: " + ex.getMessage());
                }
            }
        }
    }


    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new Interface("Polynomial Calculator");
            frame.setVisible(true);
        });
    }
}