package classes;

import java.math.BigDecimal;

public class Saque extends TransacaoBancaria{


	private void sacar(BigDecimal valorSacado) throws Exception {
		contaOrigem.sacar(valorSacado);
	}

	@Override
	public void executar(BigDecimal valor) throws Exception {
		sacar(valor);
	}
}
