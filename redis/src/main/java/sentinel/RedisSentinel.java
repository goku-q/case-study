package sentinel;

import java.util.HashSet;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class RedisSentinel {
	private RedisSentinel() {}
	private static final int MAXIDLE = 10;
	private static final int MAXTOTAL = 60;
	private static final String HOST = "192.168.0.9";
	private static volatile JedisSentinelPool jedisSentinelPool = null;
	//获取sentinel连接池
	public static JedisSentinelPool getSentinelPool() {
		if (jedisSentinelPool==null) {
			synchronized(RedisSentinel.class) {
				if (jedisSentinelPool==null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(MAXTOTAL);
					config.setMaxIdle(MAXIDLE);
					HashSet<String> set = new HashSet<String>();
					set.add("192.168.0.9:7000");
					set.add("192.168.0.9:7002");
					set.add("192.168.0.9:7001");
					jedisSentinelPool = new JedisSentinelPool("mymaster", set, config);
				}
			}
		}
		HostAndPort currentHostMaster = jedisSentinelPool.getCurrentHostMaster();
		System.out.println(currentHostMaster.toString());
		return jedisSentinelPool;
	}
	
	//获取jedis连接
	public static Jedis getResource() {
		return getSentinelPool().getResource();
	}
	//关闭连接
	public static void closeJedisPool() {
		if (jedisSentinelPool!=null) {
			jedisSentinelPool.close();
		}
	}
	
	//测试
	public static void main(String[] args) {
		Jedis jedis = RedisSentinel.getResource();
		jedis.set("hello_sentinel", "sentinel");
		System.out.println(jedis.get("hello_sentinel"));
	}
}
