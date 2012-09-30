package classes;

import java.math.BigDecimal;

public abstract class TransacaoBancaria {

	protected Conta conta;

	public void setConta(Conta poupanca) {
		conta = poupanca;
	}
	
	public abstract void executar(BigDecimal valor) throws Exception;
}
