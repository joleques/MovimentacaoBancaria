package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Banco {

	private ArrayBlockingQueue<Poupanca> popanca;
	private List<ContaCorrente> contasCorrentes;
	
	

	public Banco() {
		super();
		this.popanca = new ArrayBlockingQueue<Poupanca>(1);
		this.contasCorrentes = new ArrayList<ContaCorrente>();
	}

	public Poupanca getPopanca() throws InterruptedException {
		return popanca.take();
	}

	public void setPoupanca(Poupanca poupanca) throws InterruptedException {
		this.popanca.put(poupanca);
	}
	
	public int getTotalDeContasPoupanca(){
		return popanca.size();
	}

	public void setPoupanca(ArrayBlockingQueue<Poupanca> lista) {
		this.popanca = lista;
	}
	
	public void setContaCorrente(ContaCorrente conta){
		this.contasCorrentes.add(conta);
	}

	public ContaCorrente getContaCorrente(int index){
		return this.contasCorrentes.get(index);
	}

	public List<ContaCorrente> getTodasContaCorrente(){
		return this.contasCorrentes;
	}
	
}
