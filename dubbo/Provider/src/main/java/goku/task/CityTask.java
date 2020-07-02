package goku.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CityTask {
	@Scheduled(fixedRate = 10000)
	public void cityTask() {
		
	}

}
