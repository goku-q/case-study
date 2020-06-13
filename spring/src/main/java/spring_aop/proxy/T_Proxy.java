package spring_aop.proxy;
/**
 * java代理的原理（静态代理）
 * T_mac的代理类，它也实现了Nba接口，代理类需要实现被代理类实现的接口
 * 并且需要代理类的对象，以调用其方法
 * 此方法为静态代理，代理对象固定不灵活
 * @author tmac-q
 *
 */
public class T_Proxy implements Nba {
	//目标对象，被代理对象
	private T_mac tMac;
	

	public T_Proxy(T_mac tMac) {
		super();
		this.tMac = tMac;
	}
	public void player() {
		System.out.println("增强了T_mac的方法");
		tMac.player();
	}
	
	public static void main(String[] args) {
		T_Proxy t_Proxy = new T_Proxy(new T_mac());
		t_Proxy.player();
	}
}
