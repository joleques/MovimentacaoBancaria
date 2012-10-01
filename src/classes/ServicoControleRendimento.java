package classes;

import java.math.BigDecimal;
import java.util.concurrent.ArrayBlockingQueue;

public class ServicoControleRendimento implements Runnable {

	private Banco banco;
	private BigDecimal percentualDeReajuste;

	public ServicoControleRendimento(Banco banco) {
		this.banco = banco;
		this.percentualDeReajuste = new BigDecimal(0.08);
	}

	@Override
	public void run() {
		boolean peridoExecucao = true;
		int totalContas = banco.getTotalDeContasPoupanca();
		if (totalContas > 0) {
			while (peridoExecucao) {
				try {
					Thread.sleep(3000);
					ArrayBlockingQueue<Poupanca> lista = new ArrayBlockingQueue<Poupanca>(
							totalContas);
					for (int i = 1; i <= totalContas; i++) {
						Poupanca conta = banco.getPopanca();
						conta.adicionarReajusteMensal(percentualDeReajuste);
						lista.add(conta);
					}
					banco.setPoupanca(lista);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
