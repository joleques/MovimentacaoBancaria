package classes;

import java.math.BigDecimal;
import java.util.Random;

public abstract class Conta implements Entidade{

	private BigDecimal saldo;
	private Integer numero;

	
	
	public Conta() {
		super();
		Random gerador = new Random();
		this.numero = gerador.nextInt(900000);
	}

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

	@Override
	public Integer getCodigo() {
		return numero;
	}

}
