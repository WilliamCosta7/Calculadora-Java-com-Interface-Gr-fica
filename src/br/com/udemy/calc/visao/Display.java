package br.com.udemy.calc.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.udemy.calc.modelo.Memoria;
import br.com.udemy.calc.modelo.MemoriaObservador;

@SuppressWarnings("serial")
public class Display extends JPanel implements MemoriaObservador{
	
	private final JLabel label ;
	
	public Display() {
		// Aqui o display entra  como observador
		Memoria.getInstancia().adicionarObservador(this);
		setBackground(new Color(46,49,50));
		label = new JLabel(Memoria.getInstancia().getTextoAtual());
		label.setForeground(Color.WHITE);
		label.setFont(new Font("courier", Font.PLAIN,30));
		add(label);
		
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10 , 25));
	}
	
	@Override
	public void valorAlterado(String novoValor) {
		label.setText(novoValor);
	}

}
