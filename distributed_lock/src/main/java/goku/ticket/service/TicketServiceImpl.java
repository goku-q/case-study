package goku.ticket.service;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sun.swing.plaf.synth.DefaultSynthStyle;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TicketServiceImpl implements TicketService{
    private static final String TICKET_KEY = "TICKET_COUNT";
    private static final String TICKET_COUNT = "50";//模拟50张票，存入redis
    private static final String LOCK_KEY="LOCK_KEY";//redis 锁的key
    private static final String LOCK_ID = UUID.randomUUID().toString();//redis value，代表当前线程ID

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Redisson redisson;
    /**
     * redis虽然是单线程的，但多个线程先后拿到同一个redis中余量后，在进行count>0判断时就会出现并发安全问题
     * 解决方案：
     * 1、单服务情况下枷锁即可
     * 2、分布式架构下，加锁也避免不了并发问题，因为访问的是不同的服务器，锁只能在本地JVM中生效，使用分布式锁
     *      可以通过redis的单线程工作模式，设置key value来实现分布式加锁；key为锁名，value为当前线程ID，存在的几个问题：
     *      （1）、因服务问题加锁后宕机，永远服务释放锁；  通过给key设置过期时间
     *      （2）、枷锁的操作和添加key有效期的命令必须是原子的，否自有效期可能因宕机加不上
     *      （3）、手动释放锁的时候。用通过线程ID判断与锁的ID是否相同，避免因超时释放别人的锁
     *      （4）、过期时间太短，服务器没死但是执行慢，可以再起一个线程，定时给key expire重新设定有效期
     * 3、redisson客户端，直接支持redis分布式锁，原理如上，使用非常简单
     * @return
     */
    @Override
    public String getTicket() {

        /*Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(LOCK_KEY, LOCK_ID, 30, TimeUnit.SECONDS); //加分布式锁,相当于setnx，当这个key不存在才可以加锁，否则不可以
        if (!flag)//如果加锁没有成功则return，说明锁被别的线程占用中
            return "error";*/
        RLock lock = redisson.getLock(LOCK_KEY);//获取锁对象
        try {
            lock.lock(30,TimeUnit.SECONDS);//加锁，相当于setIfAbsent()的操作
            String result = stringRedisTemplate.opsForValue().get(TICKET_KEY);
            int count = Integer.parseInt(result);
            //如果票数大于50，可以进行抢票
            if(count>0){
                //开始抢票，将redis中余量减一
                int resultCount = count-1;
                stringRedisTemplate.opsForValue().set(TICKET_KEY,resultCount+"");
                System.out.println("抢票成功，当前票数余量"+count);
            }else {
                System.out.println("当前票数余量不足，抢票失败");
            }
            return "end";
        }finally {
            //释放锁
            lock.unlock();
            /*if (LOCK_ID.equals(stringRedisTemplate.opsForValue().get(LOCK_KEY)))//判断如果为当前线程的锁，才释放
                stringRedisTemplate.delete(LOCK_KEY);*/
        }

    }

    /**
     * 添加票数
     * @return
     */
    @Override
    public String saveTicket() {
        stringRedisTemplate.opsForValue().set(TICKET_KEY,TICKET_COUNT);
        return "添加成功";
    }
}
