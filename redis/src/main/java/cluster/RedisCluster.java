package cluster;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import single.RedisSingle;

/**
 * redis cluster
 * 
 * @author goku-q
 *
 */
public class RedisCluster {
	private RedisCluster() {}
	private static final int MAXIDLE = 10;
	private static final int MAXTOTAL = 60;
	private static final String HOST = "192.168.0.9";
	private static volatile JedisCluster jedisCluster = null;
	//创建连接池
	public static JedisCluster getJedisCluster() {
		if (jedisCluster==null) {
			synchronized(RedisSingle.class) {
				if (jedisCluster==null) {
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxIdle(MAXIDLE);
					poolConfig.setMaxTotal(MAXTOTAL);
					poolConfig.setMaxWaitMillis(10000);
					HashSet<HostAndPort> set = new HashSet<HostAndPort>();
					set.add(new HostAndPort(HOST, 30001));
					set.add(new HostAndPort(HOST, 30002));
					set.add(new HostAndPort(HOST, 30003));
					set.add(new HostAndPort(HOST, 30004));
					set.add(new HostAndPort(HOST, 30005));
					set.add(new HostAndPort(HOST, 30006));
					jedisCluster = new JedisCluster(set,poolConfig);
				}
			}
		}
		return jedisCluster;
	}
	//关闭连接池
	public static void close() {
		if (jedisCluster!=null) {
			jedisCluster.close();
		}
	}
	//test
	public static void main(String[] args) {
		JedisCluster cluster = RedisCluster.getJedisCluster();
		cluster.set("hello", "jedis");
		cluster.set("hello_cluster", "cluster");
		System.out.println(cluster.get("hello"));
		System.out.println(cluster.get("hello_cluster"));
	}
}
