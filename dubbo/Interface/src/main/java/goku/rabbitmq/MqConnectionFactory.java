package goku.rabbitmq;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Command;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ConsumerShutdownSignalCallback;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.Method;
import com.rabbitmq.client.ReturnCallback;
import com.rabbitmq.client.ReturnListener;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Basic.RecoverOk;
import com.rabbitmq.client.AMQP.Confirm.SelectOk;
import com.rabbitmq.client.AMQP.Exchange.BindOk;
import com.rabbitmq.client.AMQP.Exchange.DeclareOk;
import com.rabbitmq.client.AMQP.Exchange.DeleteOk;
import com.rabbitmq.client.AMQP.Exchange.UnbindOk;
import com.rabbitmq.client.AMQP.Queue.PurgeOk;
import com.rabbitmq.client.AMQP.Tx.CommitOk;
import com.rabbitmq.client.AMQP.Tx.RollbackOk;
/**
 * 测试rabbitmq使用
 * @author tmac-q
 *
 */
public class MqConnectionFactory {

	private MqConnectionFactory() {}
	private static volatile Connection connection;
	
	public static Connection getConnection() throws IOException, TimeoutException {
		if (connection==null) {
			synchronized (MqConnectionFactory.class) {
				if (connection==null) {
					ConnectionFactory factory = new ConnectionFactory();
					factory.setHost("192.168.0.9");//设置主机地址
					factory.setPort(5672);//设置端口号，默认5672
					factory.setVirtualHost("/");//设置虚拟主机
					//创建连接
					connection = factory.newConnection();
				}
			}
		}
		
		return connection;
	}
	
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection2 = MqConnectionFactory.getConnection();
		Channel channel = connection2.createChannel();
	}

}