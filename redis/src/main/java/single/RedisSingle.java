package single;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 连接工厂
 * @author tmac-q
 *
 */
public class RedisSingle {
	private RedisSingle() {}
	private static final int MAXIDLE = 10;
	private static final int MAXTOTAL = 60;
	private static final String HOST = "192.168.0.9";
	private static volatile JedisPool jedisPool = null;
	//创建连接池
	public static JedisPool getJedisPool() {
		if (jedisPool==null) {
			synchronized(RedisSingle.class) {
				if (jedisPool==null) {
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxIdle(MAXIDLE);
					poolConfig.setMaxTotal(MAXTOTAL);
					poolConfig.setMaxWaitMillis(10000);
					jedisPool = new JedisPool(poolConfig,HOST);
				}
			}
		}
		return jedisPool;
	}
		
	//获取连接
	public static Jedis getJedis() {
		return getJedisPool().getResource();
	}
	//关闭连接池
	public static void close() {
		if (jedisPool!=null) {
			jedisPool.close();
		}
	}
	//test
	public static void main(String[] args) {
		Jedis jedis = RedisSingle.getJedis();
		jedis.set("hello", "redis");
		jedis.get("hello");
	}
		
}
