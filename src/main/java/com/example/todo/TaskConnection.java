package com.example.todo;

import java.util.List;

import graphql.relay.Connection;
import graphql.relay.Edge;
import graphql.relay.PageInfo;

public class TaskConnection implements Connection<Task> {

	@Override
	public List<Edge<Task>> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo getPageInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
