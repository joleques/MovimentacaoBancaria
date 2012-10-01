package classes;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MovimentacaoTest {

	private static final BigDecimal VALOR_TRANSACAO = new BigDecimal(20.0);
	private static final BigDecimal SALDO_CONTA = new BigDecimal(100.0);
	TransacaoBancaria movimentacao;

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		movimentacao = null;
	}

	@Test
	public void sacarComSucesso() {
		try {
			// DADO QUE EU QUEIRA SACAR 20 REAIS, E TENHA SALDO SUFICIENTE
			Conta poupanca = new Poupanca();
			poupanca.setSaldo(SALDO_CONTA);			
			BigDecimal valorSacado = VALOR_TRANSACAO;
			movimentacao = new Saque();

			// QUANDO EU EXECUTAR A MOVIMENTAÇÃO FINACEIRA
			movimentacao.setConta(poupanca);
			movimentacao.executar(valorSacado);

			// ENTÃO MEU SALDO DEVE SER DE 80 REAIS
			Assert.assertEquals((SALDO_CONTA.subtract(VALOR_TRANSACAO)),poupanca.getSaldo());
		} catch (Exception e) {
			Assert.fail("Não podia ter falhado o saque pois existe saldo.");
		}
	}

	@Test
	public void depositarComSucesso() {
		try {
			// DADO QUE EU QUEIRA DEPOSITAR 20 REAIS
			Conta poupanca = new Poupanca();
			poupanca.setSaldo(SALDO_CONTA);			
			BigDecimal valorDeposito = VALOR_TRANSACAO;
			movimentacao = new Deposito();

			// QUANDO EU EXECUTAR A MOVIMENTAÇÃO FINACEIRA
			movimentacao.setConta(poupanca);
			movimentacao.executar(valorDeposito);

			// ENTÃO MEU SALDO DEVE SER DE 120 REAIS
			Assert.assertEquals((SALDO_CONTA.add(VALOR_TRANSACAO)),poupanca.getSaldo());
		} catch (Exception e) {
			Assert.fail("Não podia ter falhado o saque pois o deposito esta ok.");
		}
	}

	@Test
	public void depositarPassandoValorNulo() {
		try {
			// DADO QUE EU QUEIRA DEPOSITAR UM VALOR NULO A MINHA CONTA NÃO DEVE SE ALTERAR
			Conta poupanca = new Poupanca();
			poupanca.setSaldo(SALDO_CONTA);			
			BigDecimal valorDeposito = null;
			movimentacao = new Deposito();

			// QUANDO EU EXECUTAR A MOVIMENTAÇÃO FINACEIRA
			movimentacao.setConta(poupanca);
			movimentacao.executar(valorDeposito);

			// ENTÃO MEU SALDO DEVE SER DE 100 REAIS
			Assert.assertEquals(SALDO_CONTA,poupanca.getSaldo());
		} catch (Exception e) {
			Assert.fail("Não podia ter falhado o saque pois o deposito esta ok.");
		}
	}

	@Test
	public void sacarSemSaldo() {
		try {
			// DADO QUE EU QUEIRA SACAR 20 REAIS, E TENHA SALDO INSUFICIENTE
			Conta poupanca = new Poupanca();
			poupanca.setSaldo(new BigDecimal(0));
			BigDecimal valorSacado = VALOR_TRANSACAO;
			movimentacao = new Saque();

			// QUANDO EU EXECUTAR A MOVIMENTAÇÃO FINACEIRA
			movimentacao.setConta(poupanca);
			movimentacao.executar(valorSacado);
			Assert.fail("Não podia ter executado o saque sem saldo ou com saldo insuficiente.");
		} catch (Exception e) {
			// ENTÃO MEU SALDO DEVE SER DE 80 REAIS
			Assert.assertEquals("Saldo Insuficiente.", e.getMessage());
		}
	}

}
