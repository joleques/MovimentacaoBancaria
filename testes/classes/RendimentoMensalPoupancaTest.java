package classes;

import java.math.BigDecimal;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RendimentoMensalPoupancaTest {

	private static final BigDecimal RENDIMENTO_PRIMEIRO_MES_PRIMEIRA_CONTA = new BigDecimal(10.8);
	private static final BigDecimal RENDIMENTO_PRIMEIRO_MES_SEGUNDA_CONTA = new BigDecimal(108.001);

	private static final BigDecimal RENDIMENTO_SEGUNDA_MES_PRIMEIRA_CONTA = new BigDecimal(11.68);
	private static final BigDecimal RENDIMENTO_SEGUNDA_MES_SEGUNDA_CONTA = new BigDecimal(116.66);
	
	private static final BigDecimal SALDO = new BigDecimal(10.0);
	private static final BigDecimal SALDO_SEGUNDA = new BigDecimal(100.0);
	
	private ServicoControleRendimento servicoRendimentoDaPoupanca;
	private Banco Santander;

	@Before
	public void setUp() throws Exception {
		Santander = new Banco();
	}

	@After
	public void tearDown() throws Exception {
		servicoRendimentoDaPoupanca = null;
		Santander = null;
	}

	@Test
	public void testarResndimentoApos30Segundos() {
		try {
			// DADO QUE O BANCO POSSUA CONTAS POUPANCA CADASTRADAS E TENHA PASSADO 30 SEGUNDOS
			setUpDasInformacoesDoBanco();
			servicoRendimentoDaPoupanca = new ServicoControleRendimento(Santander);

			// QUANDO EU CONSULTAR O SALDO DAS CONTAS
			ExecutorService executarAplicacao = Executors.newCachedThreadPool();
			executarAplicacao.execute(servicoRendimentoDaPoupanca);
			Thread.sleep(4000);

			// ENTÃO OS SALDOS DEVEM ESTAR AUMENTADOS EM 0,8%
			Poupanca primeiraPoupanca = Santander.getPopanca();
			Assert.assertEquals(RENDIMENTO_PRIMEIRO_MES_PRIMEIRA_CONTA.setScale(2, BigDecimal.ROUND_UP),primeiraPoupanca.getSaldo());
			
			Poupanca segundaPoupanca = Santander.getPopanca();
			Assert.assertEquals(RENDIMENTO_PRIMEIRO_MES_SEGUNDA_CONTA.setScale(2, BigDecimal.ROUND_UP),segundaPoupanca.getSaldo());
			
			executarAplicacao.shutdown();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testarResndimentoApos60Segundos() {
		try {
			// DADO QUE O BANCO POSSUA CONTAS POUPANCA CADASTRADAS E TENHA PASSADO 60 SEGUNDOS
			setUpDasInformacoesDoBanco();
			servicoRendimentoDaPoupanca = new ServicoControleRendimento(Santander);

			// QUANDO EU CONSULTAR O SALDO DAS CONTAS
			ExecutorService executarAplicacao = Executors.newCachedThreadPool();
			executarAplicacao.execute(servicoRendimentoDaPoupanca);
			Thread.sleep(7000);

			// ENTÃO OS SALDOS DEVEM ESTAR AUMENTADOS EM 0,8%
			Poupanca primeiraPoupanca = Santander.getPopanca();
			Assert.assertEquals(RENDIMENTO_SEGUNDA_MES_PRIMEIRA_CONTA.setScale(2, BigDecimal.ROUND_UP),primeiraPoupanca.getSaldo());
			
			Poupanca segundaPoupanca = Santander.getPopanca();
			Assert.assertEquals(RENDIMENTO_SEGUNDA_MES_SEGUNDA_CONTA.setScale(2, BigDecimal.ROUND_UP),segundaPoupanca.getSaldo());
			
			executarAplicacao.shutdown();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testarResndimentoSemContasPoupanca() {
		try {
			// DADO QUE O BANCO POSSUA CONTAS POUPANCA CADASTRADAS E TENHA PASSADO 30 SEGUNDOS
			servicoRendimentoDaPoupanca = new ServicoControleRendimento(Santander);

			// QUANDO EU CONSULTAR O SALDO DAS CONTAS
			ExecutorService executarAplicacao = Executors.newCachedThreadPool();
			executarAplicacao.execute(servicoRendimentoDaPoupanca);
			Thread.sleep(4000);

			// ENTÃO OS SALDOS DEVEM ESTAR AUMENTADOS EM 0,8%
			Assert.assertTrue(Santander.getTotalDeContasPoupanca() == 0);			
			executarAplicacao.shutdown();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	private void setUpDasInformacoesDoBanco() throws InterruptedException {
		ArrayBlockingQueue<Poupanca> lista = new ArrayBlockingQueue<Poupanca>(2);
		
		Poupanca primeira = new Poupanca();
		primeira.setSaldo(SALDO);		
		lista.put(primeira);
		

		Poupanca segunda = new Poupanca();
		segunda.setSaldo(SALDO_SEGUNDA);	
		lista.put(segunda);
		
		Santander.setPoupanca(lista);		

	}

}
