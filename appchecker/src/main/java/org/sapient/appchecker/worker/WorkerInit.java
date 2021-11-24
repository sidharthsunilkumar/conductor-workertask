package org.sapient.appchecker.worker;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;

@Component
public class WorkerInit {
	@Autowired
	TaskClient taskClient;

	@EventListener(ContextRefreshedEvent.class)
	public void initConductorPolling() {
		int threadCount = 1;
		Worker worker1 = new SampleWorker("applicationcheck");
		// Create TaskRunnerConfigurer
		TaskRunnerConfigurer configurer = new TaskRunnerConfigurer.Builder(taskClient, Arrays.asList(worker1))
				.withThreadCount(threadCount).build();

		// Start the polling and execution of tasks
		configurer.init();
		System.out.println("STARTED POLLING");
	}
}
