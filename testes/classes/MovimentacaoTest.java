package classes;

import java.math.BigDecimal;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
			movimentacao.setContaOrigem(poupanca);
			movimentacao.executar(valorSacado);

			// ENTÃO MEU SALDO DEVE SER DE 80 REAIS
			Assert.assertEquals((SALDO_CONTA.subtract(VALOR_TRANSACAO)),
					poupanca.getSaldo());
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
			movimentacao.setContaOrigem(poupanca);
			movimentacao.executar(valorDeposito);

			// ENTÃO MEU SALDO DEVE SER DE 120 REAIS
			Assert.assertEquals((SALDO_CONTA.add(VALOR_TRANSACAO)),
					poupanca.getSaldo());
		} catch (Exception e) {
			Assert.fail("Não podia ter falhado o saque pois o deposito esta ok.");
		}
	}

	@Test
	public void depositarPassandoValorNulo() {
		try {
			// DADO QUE EU QUEIRA DEPOSITAR UM VALOR NULO A MINHA CONTA NÃO DEVE
			// SE ALTERAR
			Conta poupanca = new Poupanca();
			poupanca.setSaldo(SALDO_CONTA);
			BigDecimal valorDeposito = null;
			movimentacao = new Deposito();

			// QUANDO EU EXECUTAR A MOVIMENTAÇÃO FINACEIRA
			movimentacao.setContaOrigem(poupanca);
			movimentacao.executar(valorDeposito);

			// ENTÃO MEU SALDO DEVE SER DE 100 REAIS
			Assert.assertEquals(SALDO_CONTA, poupanca.getSaldo());
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
			movimentacao.setContaOrigem(poupanca);
			movimentacao.executar(valorSacado);
			Assert.fail("Não podia ter executado o saque sem saldo ou com saldo insuficiente.");
		} catch (Exception e) {
			// ENTÃO MEU SALDO DEVE SER DE 80 REAIS
			Assert.assertEquals("Saldo Insuficiente.", e.getMessage());
		}
	}

	@Test
	public void deveTransferir20ReaisDaContaCorrenteParaContaPoupanca() {
		try {
			ContaCorrente contaOrigem = criarContaCorrente(SALDO_CONTA, null);
			Poupanca contaDestino = criarContaPoupanca(SALDO_CONTA, null);

			movimentacao = new Transferencia(contaOrigem, contaDestino);
			quandoExecutarAhMovimentacaoAhTransferenciaDeveTerSidoExecutadoComSucesso(
					contaOrigem, contaDestino);
		} catch (Exception e) {
			Assert.fail("Não podia ter falhado o saque pois o deposito esta ok.");
		}
	}

	@Test
	public void deveTransferir20ReaisDaContaPoupancaParaContaCorrente() {
		try {
			Poupanca contaOrigem = criarContaPoupanca(SALDO_CONTA, null);
			ContaCorrente contaDestino = criarContaCorrente(SALDO_CONTA, null);

			movimentacao = new Transferencia(contaOrigem, contaDestino);
			quandoExecutarAhMovimentacaoAhTransferenciaDeveTerSidoExecutadoComSucesso(
					contaOrigem, contaDestino);
		} catch (Exception e) {
			Assert.fail("Não podia ter falhado o saque pois o deposito esta ok.");
		}
	}

	@Test
	public void deveTransferir20ReaisDaContaCorrenteParaContaCorrente() {
		try {
			ContaCorrente contaOrigem = criarContaCorrente(SALDO_CONTA, null);
			ContaCorrente contaDestino = criarContaCorrente(SALDO_CONTA, null);

			movimentacao = new Transferencia(contaOrigem, contaDestino);
			quandoExecutarAhMovimentacaoAhTransferenciaDeveTerSidoExecutadoComSucesso(
					contaOrigem, contaDestino);
		} catch (Exception e) {
			Assert.fail("Não podia ter falhado o saque pois o deposito esta ok.");
		}
	}

	@Test
	public void deveRealizarTodasTransacoesEmDiversasContasPor30Segundos() {
		try {
			Banco santander = new Banco();
			ArrayBlockingQueue<Poupanca> listaPoupancas = new ArrayBlockingQueue<Poupanca>(2);
			
			Poupanca poupancaJoao = criarContaPoupanca(new BigDecimal(100.0), "Joao Paulo");
			listaPoupancas.put(poupancaJoao);
			
			Poupanca poupancaPaulo = criarContaPoupanca(new BigDecimal(200.0), "Paulo Roberto");
			listaPoupancas.put(poupancaPaulo);

			santander.setPoupanca(listaPoupancas);
			
			ContaCorrente contaMarina = criarContaCorrente(new BigDecimal(300.0), "Marina Silva");
			santander.setContaCorrente(contaMarina);
			
			ContaCorrente contaMarcia = criarContaCorrente(new BigDecimal(250.0), "Marcia Sorrentino");
			santander.setContaCorrente(contaMarcia);
			
			ServicoControleRendimento servicoRendimentoDaPoupanca = new ServicoControleRendimento(santander);			
			ServicoTransacoes servicoTransacoes = new ServicoTransacoes();
			
			
			ExecutorService executarAplicacao = Executors.newCachedThreadPool();
			executarAplicacao.execute(servicoRendimentoDaPoupanca);
			
			for (int i = 0; i < 15; i++) {
				Thread.sleep(2000);
				realizarDeposito(contaMarcia, new BigDecimal(2.0), servicoTransacoes);			
				realizarSaque(poupancaPaulo,new BigDecimal(1.0), servicoTransacoes);			
				realizarTransferencia(poupancaJoao, contaMarina,new BigDecimal(1.5), servicoTransacoes);
				executarAplicacao.execute(servicoTransacoes);
				
				printDadosConta(poupancaJoao);
				printDadosConta(poupancaPaulo);
				printDadosConta(contaMarina);
				printDadosConta(contaMarcia);
			}
			executarAplicacao.shutdown();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void printDadosConta(Conta poupancaJoao) {
		System.out.println("Conta : "+ poupancaJoao.getCodigo());
		System.out.println("Nome : "+ poupancaJoao.getNome());
		System.out.println("Saldo : "+ poupancaJoao.getSaldo());
		System.out.println("\n ----------------------------------------");
	}

	private void realizarTransferencia(Poupanca contaDestino,
			ContaCorrente contaOrigem, BigDecimal valor, ServicoTransacoes servicoTransacoes) {
		Transferencia transferencia = new Transferencia(contaOrigem, contaDestino);
		servicoTransacoes.setTransacao(transferencia,valor);
	}

	private void realizarSaque(Conta conta,BigDecimal valor, ServicoTransacoes servicoTransacoes) {
		Saque saque = new Saque();
		saque.setContaOrigem(conta);
		servicoTransacoes.setTransacao(saque, valor);
	}

	private void realizarDeposito(Conta conta,BigDecimal valor, ServicoTransacoes servicoTransacoes) {
		Deposito deposito = new Deposito();
		deposito.setContaOrigem(conta);
		servicoTransacoes.setTransacao(deposito,valor);
	}

	private ContaCorrente criarContaCorrente(BigDecimal saldo, String nomeProprietario) {
		ContaCorrente contaOrigem = new ContaCorrente();
		contaOrigem.setSaldo(saldo);
		contaOrigem.setNome(nomeProprietario);
		return contaOrigem;
	}

	private Poupanca criarContaPoupanca(BigDecimal saldo, String nomeProprietario) {
		Poupanca poupanca = new Poupanca();
		poupanca.setSaldo(saldo);
		poupanca.setNome(nomeProprietario);
		return poupanca;
	}
	
	private void quandoExecutarAhMovimentacaoAhTransferenciaDeveTerSidoExecutadoComSucesso(Conta contaOrigem, Conta contaDestino) throws Exception {
		BigDecimal valorTransferir = VALOR_TRANSACAO;
		movimentacao.executar(valorTransferir);
		Assert.assertEquals((SALDO_CONTA.add(VALOR_TRANSACAO)),contaDestino.getSaldo());
		Assert.assertEquals((SALDO_CONTA.subtract(VALOR_TRANSACAO)),contaOrigem.getSaldo());
	}


}
