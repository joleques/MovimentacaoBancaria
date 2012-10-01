package classes;

import java.util.concurrent.ArrayBlockingQueue;

public class Banco {

	private ArrayBlockingQueue<Poupanca> popanca;
	
	

	public Banco() {
		super();
		this.popanca = new ArrayBlockingQueue<Poupanca>(1);
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
	
}
