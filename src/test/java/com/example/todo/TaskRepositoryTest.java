package com.example.todo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskRepositoryTest {

	private final TaskRepository sut = new TaskRepository();
	private final List<Task> tasks = new ArrayList<>();

	@BeforeEach
	void init() {
		// https://ja.wikipedia.org/wiki/メタ構文変数
		tasks.add(sut.newTask("foo"));
		tasks.add(sut.newTask("bar"));
		tasks.add(sut.newTask("baz"));
		tasks.add(sut.newTask("qux"));
		tasks.add(sut.newTask("quux"));
		tasks.add(sut.newTask("corge"));
		tasks.add(sut.newTask("grault"));
		tasks.add(sut.newTask("garply"));
		tasks.add(sut.newTask("waldo"));
	}

	@Test
	void findAll() throws Exception {
		List<Task> result = sut.find(100, null);
		assertEquals(tasks, result);
	}

	@Test
	void first() throws Exception {
		List<Task> result = sut.find(3, null);
		assertEquals(tasks.subList(0, 3), result);
	}

	@Test
	void firstAfter() throws Exception {
		String after = tasks.get(2).getId();
		List<Task> result = sut.find(3, after);
		assertEquals(tasks.subList(3, 6), result);
	}

	@Test
	void afterNotExists() throws Exception {
		String after = "NotExists";
		List<Task> result = sut.find(3, after);
		assertEquals(List.of(), result);
	}
}
