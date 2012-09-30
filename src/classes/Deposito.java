package classes;

import java.math.BigDecimal;

public class Deposito extends TransacaoBancaria{

	private void depositar(BigDecimal valorDeposito) {
		conta.depositar(valorDeposito);
	}

	@Override
	public void executar(BigDecimal valor) throws Exception {
		depositar(valor);
	}
}
