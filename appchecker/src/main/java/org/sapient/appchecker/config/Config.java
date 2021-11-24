package org.sapient.appchecker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.http.WorkflowClient;

@Configuration
public class Config {
	String conductorServerUri = "http://localhost:8080/api/";

	@Bean
	public MetadataClient getMetadataClient() {
		MetadataClient metadataClient = new MetadataClient();
		metadataClient.setRootURI(conductorServerUri);
		return metadataClient;
	}

	@Bean
	public WorkflowClient getWorkflowClient() {
		WorkflowClient workflowClient = new WorkflowClient();
		workflowClient.setRootURI(conductorServerUri);
		return workflowClient;
	}

	@Bean
	public TaskClient getTaskClient() {
		TaskClient taskClient = new TaskClient();
		taskClient.setRootURI(conductorServerUri);
		return taskClient;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
