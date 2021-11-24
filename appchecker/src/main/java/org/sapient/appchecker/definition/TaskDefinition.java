package org.sapient.appchecker.definition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskDef.RetryLogic;
import com.netflix.conductor.common.metadata.tasks.TaskDef.TimeoutPolicy;

@Component
public class TaskDefinition {

	@Autowired
	TaskClient taskClient;
	@Autowired
	MetadataClient metaDataClient;

	private TaskDef createApplicationCheckTask() {
		TaskDef taskDef = new TaskDef("applicationcheck", "Phase 1 application checker");
		taskDef.setResponseTimeoutSeconds(300);
		taskDef.setRetryLogic(RetryLogic.FIXED);
		taskDef.setRetryCount(1);
		taskDef.setRetryDelaySeconds(60);
		taskDef.setTimeoutPolicy(TimeoutPolicy.TIME_OUT_WF);
		taskDef.setTimeoutSeconds(1200);
		taskDef.setOwnerEmail("test@gmail.com");
		List<String> keys = new ArrayList();
		keys.add("id");
		keys.add("name");
		keys.add("subject1");
		keys.add("subject2");
		keys.add("subject3");
		taskDef.setInputKeys(keys);
		return taskDef;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void registerTaskDefinition() {
		List<TaskDef> taskDefList = new ArrayList();
		taskDefList.add(createApplicationCheckTask());
		metaDataClient.registerTaskDefs(taskDefList);
		System.out.println("Registering Task Definitions Done");
	}
}
