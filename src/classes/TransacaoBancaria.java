package classes;

import java.math.BigDecimal;

public abstract class TransacaoBancaria {

	protected Conta contaOrigem;

	public void setContaOrigem(Conta conta) {
		contaOrigem = conta;
	}
	
	public abstract void executar(BigDecimal valor) throws Exception;
}
