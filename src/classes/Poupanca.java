package classes;

import java.math.BigDecimal;

public class Poupanca extends Conta{

	public Poupanca() {
		super();
	}
	
	public void adicionarReajusteMensal(BigDecimal percentualReajuste){
		this.setSaldo(calcularNovoSaldo(this.getSaldo(), percentualReajuste));
	}

	private BigDecimal calcularNovoSaldo(BigDecimal saldo,BigDecimal percentualReajuste) {
		return saldo.add(saldo.multiply(percentualReajuste)).setScale(2, BigDecimal.ROUND_UP);
	}

	
}
