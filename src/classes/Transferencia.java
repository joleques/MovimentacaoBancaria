package classes;

import java.math.BigDecimal;

public class Transferencia extends TransacaoBancaria {

	private Conta contaDestino;
	private Saque saque;
	private Deposito deposito;
	
	public Transferencia(ContaCorrente contaOrigem, Poupanca contaDestino) {
		setarAtributos(contaOrigem, contaDestino);
	}

	public Transferencia(Poupanca contaOrigem, ContaCorrente contaDestino) {
		setarAtributos(contaOrigem, contaDestino);
	}

	public Transferencia(ContaCorrente contaOrigem,ContaCorrente contaDestino) {
		setarAtributos(contaOrigem, contaDestino);
	}

	private void setarAtributos(Conta contaOrigem, Conta contaDestino) {
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
		this.saque = new Saque();
		this.deposito = new Deposito();
	}

	@Override
	public void executar(BigDecimal valor) throws Exception {
		sacarNaOrigem(valor);		
		depositarNoDestino(valor);
	}

	private void depositarNoDestino(BigDecimal valor) throws Exception {
		deposito.setContaOrigem(contaDestino);
		deposito.executar(valor);
	}

	private void sacarNaOrigem(BigDecimal valor) throws Exception {
		saque.setContaOrigem(contaOrigem);
		saque.executar(valor);
	}

}
