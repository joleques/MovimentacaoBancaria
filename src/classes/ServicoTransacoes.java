package classes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ServicoTransacoes implements Runnable {

	private Map<TransacaoBancaria, BigDecimal> transacaoBancaria;
	
	

	public ServicoTransacoes() {
		transacaoBancaria = new HashMap<TransacaoBancaria, BigDecimal>();
	}

	public void setTransacao(TransacaoBancaria transacao, BigDecimal valor){
		this.transacaoBancaria.put(transacao, valor);
	}

	@Override
	public void run() {
		try {
			for (TransacaoBancaria chave : this.transacaoBancaria.keySet()) {
				BigDecimal valor = this.transacaoBancaria.get(chave);
				chave.executar(valor);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
