package goku.task;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactoryConfigurationUtils;
import org.springframework.amqp.rabbit.connection.ConnectionFactoryUtils;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jca.cci.connection.SingleConnectionFactory;

import goku.listener.UserMqListener;

@Configuration
public class RabbitMQConfig {
	public static final String EX_CHANGE = "add_city";//rabbitmq交换机设定
	public static final String ADD_QUEUE = "add_queue";//添加city的队列
	public static final String FINISHED_QUEUE = "finished_queue";//完成添加city的队列
	public static final String ADD_ROUTING_KEY = "add";//添加 city的routeKey
	public static final String FINISHED_ROUTING_KEY = "finished";//完成添加的routeKey

	/**
	 * 注入交换机
	 * 
	 * @return
	 */
	@Bean(EX_CHANGE)
	public Exchange exchange() {
		System.out.println("exchange创建成功");
		return ExchangeBuilder.topicExchange(EX_CHANGE).durable(true).build();
	}

	/**
	 * 注入添加队列
	 * 
	 * @return
	 */
	@Bean(ADD_QUEUE)
	public Queue add_queue() {
		return new Queue(ADD_QUEUE, true);
	}

	/**
	 * 注入完成队列
	 * 
	 * @return
	 */
	@Bean(FINISHED_QUEUE)
	public Queue finished_queue() {
		return new Queue(FINISHED_QUEUE, true);
	}

	/**
	 * 将添加队列绑定交换机，及routekey
	 * 
	 * @param queue
	 * @param exchange
	 * @return
	 */
	@Bean
	public Binding binding_add(@Qualifier(ADD_QUEUE) Queue queue, @Qualifier(EX_CHANGE) Exchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ADD_ROUTING_KEY).noargs();
	}

	/**
	 * 将完成队列绑定交换机，及routekey
	 * 
	 * @param queue
	 * @param exchange
	 * @return
	 */
	@Bean
	public Binding binding_finished(@Qualifier(FINISHED_QUEUE) Queue queue, @Qualifier(EX_CHANGE) Exchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(FINISHED_ROUTING_KEY).noargs();
	}

	/**
	 * 注入rabbitmq工厂
	 * 
	 * @return
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost("192.168.0.9");//设置主机地址
		connectionFactory.setPort(5672);//设置端口号，默认5672
		connectionFactory.setVirtualHost("/");//设置虚拟主机
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		return connectionFactory;
	}

	/**
	 * 注入RabbitMQTemplate
	 */
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}

	@Bean
	public SimpleMessageListenerContainer listenerContainer(UserMqListener userMqListener) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(FINISHED_QUEUE);
		container.setMessageListener(userMqListener);
		return container;
	}
}
