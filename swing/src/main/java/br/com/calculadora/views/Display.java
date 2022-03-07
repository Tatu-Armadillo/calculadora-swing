package br.com.calculadora.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.calculadora.models.Memoria;
import br.com.calculadora.models.MemoriaObservador;

public class Display extends JPanel implements MemoriaObservador{

    private final JLabel label;

    public Display() {
        Memoria.getInstancia().adicionarObservador(this);
        
        setBackground(new Color(46, 49, 50));
        label = new JLabel(Memoria.getInstancia().getTextoAutal());
        label.setForeground(Color.WHITE);
        label.setFont(new Font("courier", Font.PLAIN, 30));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));
        add(label);
    }

    @Override
    public void valorAlterado(String novoValor) {
        label.setText(novoValor);        
    }
}
