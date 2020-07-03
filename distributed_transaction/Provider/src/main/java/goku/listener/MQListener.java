package goku.listener;
import java.util.Map;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import goku.service.CityService;
import goku.task.RabbitMQConfig;
/**
 * 监听User发来的开启分布式事务认信息
 * @author tmac-q
 *
 */
@Component
public class MQListener implements ChannelAwareMessageListener{
	@Autowired
	private CityService CityService;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	public void onMessage(Message message, Channel channel) throws Exception {
		byte[] body = message.getBody();
		MessageProperties messageProperties = message.getMessageProperties();
		//处理json字符串
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String ,String> map = objectMapper.readValue(new String(body), Map.class);
		System.out.println(map.get("id"));
		System.out.println(map.get("userId"));
		//验证数据库，是否为重复消息
		boolean flag = CityService.saveUserCity(map.get("city"), Integer.parseInt(map.get("userId")), map.get("id"));
		if (flag) {
			//给发送小法放发送事务完成信息
			rabbitTemplate.convertAndSend(RabbitMQConfig.EX_CHANGE, RabbitMQConfig.FINISHED_ROUTING_KEY, map.get("id"));
		}
	}
}
