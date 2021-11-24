package org.sapient.appchecker.worker;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;

public class SampleWorker implements Worker {

	private final String taskDefName;

	public SampleWorker(String taskDefName) {
		this.taskDefName = taskDefName;
	}

	@Override
	public String getTaskDefName() {
		return taskDefName;
	}

	@Override
	public TaskResult execute(Task task) {
		System.out.println(task.getTaskDefName() + "Worker Called");
		
		if (task.getTaskDefName().equals("applicationcheck")) {
			TaskResult result = checkApplication(task);
			result.setStatus(Status.COMPLETED);
			return result;
		}
		else{
			TaskResult result = new TaskResult(task);
			result.setStatus(Status.FAILED);
			return result;
		}
		
	}

	public TaskResult checkApplication(Task task){
		TaskResult result = new TaskResult(task);

		String id=(String)task.getInputData().get("id");
		String sub1=(String)task.getInputData().get("subject1");
		String sub2=(String)task.getInputData().get("subject2");
		String sub3=(String)task.getInputData().get("subject3");
		if(id.equals("1")||id.equals("2")){
			if(sub1.equalsIgnoreCase(sub2) || sub2.equalsIgnoreCase(sub3) || sub3.equalsIgnoreCase(sub1)){
				result.getOutputData().put("output1", "false");
			}
			else{
				result.getOutputData().put("output1", "true");
			}
		}
		else{
			result.getOutputData().put("output1", "false");
		}
		return result;
	}

}
