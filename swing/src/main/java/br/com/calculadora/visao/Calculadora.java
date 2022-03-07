package br.com.calculadora.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Calculadora extends JFrame {

    public static void main(String[] args) {
        new Calculadora();
    }

    public Calculadora() {
        organizarLayout();

        setSize(232, 322);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void organizarLayout() {
        setLayout(new BorderLayout());

        Display display = new Display();
        display.setPreferredSize(new Dimension(233, 60));
        add(display, BorderLayout.NORTH);

        Teclado teclado = new Teclado();
        add(teclado, BorderLayout.CENTER);
    }

}
