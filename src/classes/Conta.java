package classes;

import java.math.BigDecimal;

public class Conta {

	private BigDecimal saldo;

	public void setSaldo(BigDecimal saldoConta) {
		saldo = saldoConta;
	}

	public synchronized BigDecimal getSaldo() {
		return saldo;
	}

	public synchronized void sacar(BigDecimal valorSacado) throws Exception {
		BigDecimal saque = saldo.subtract(valorSacado);
		possuiSaldoPara(saque);
		saldo = saque;
	}

	private void possuiSaldoPara(BigDecimal saque) throws Exception {
		if (saque.compareTo(new BigDecimal(0)) < 0) throw new Exception("Saldo Insuficiente.");
	}

	public void depositar(BigDecimal valorDeposito) {
		if(valorDeposito != null){
			saldo = saldo.add(valorDeposito);
		}
	}

}
