package com.example.todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class TaskRepository {

	private final List<Task> values = Collections.synchronizedList(new ArrayList<>());

	public List<Task> find(int first, String after) {
		// select * from task where id > :after offset :first
		Stream<Task> ts = values.stream();
		if (after != null) {
			ts = ts.dropWhile(a -> a.getId().equals(after) == false).skip(1);
		}
		return ts.limit(first).collect(Collectors.toList());
	}

	public Task newTask(TaskText text) {
		Task task = new Task();
		task.setId(UUID.randomUUID().toString());
		task.setText(text);
		task.setStatus(TaskStatus.TODO);
		values.add(task);
		return task;
	}

	public Optional<Task> updateTaskStatus(String id, TaskStatus status) {
		Optional<Task> opt = values.stream().filter(a -> a.getId().equals(id)).findAny();
		opt.ifPresent(task -> task.setStatus(status));
		return opt;
	}

	private Task newTaskInternal(String text) {
		return newTask(new TaskText(text));
	}

	@PostConstruct
	public void init() {
		// https://ja.wikipedia.org/wiki/メタ構文変数
		newTaskInternal("foo");
		newTaskInternal("bar");
		newTaskInternal("baz");
		newTaskInternal("qux");
		newTaskInternal("quux");
	}
}
