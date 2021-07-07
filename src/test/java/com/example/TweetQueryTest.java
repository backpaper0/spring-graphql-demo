package com.example;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class TweetQueryTest {

	@Autowired
	private GraphQlTester graphQlTester;

	@Test
	void book() {
		String query = "{" +
				"  timeline(id: \"739d27f4-a946-4117-8efb-1156fdfcb994\") {" +
				"    tweets {" +
				"      text" +
				"      user {" +
				"        name" +
				"      }" +
				"    }" +
				"  }" +
				"}";

		graphQlTester.query(query)
				.execute()

				.path("timeline.tweets")
				.entityList(TweetDto.class)
				.hasSize(8)
				.contains(new TweetDto("ねむい", new UserDto("NONOA")));
	}

	public static class TweetDto {

		private String text;
		private UserDto user;

		public TweetDto() {
		}

		public TweetDto(String text, UserDto user) {
			this.text = text;
			this.user = user;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public UserDto getUser() {
			return user;
		}

		public void setUser(UserDto user) {
			this.user = user;
		}

		@Override
		public int hashCode() {
			return Objects.hash(text, user);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			TweetDto other = (TweetDto) obj;
			return Objects.equals(text, other.text) && Objects.equals(user, other.user);
		}
	}

	public static class UserDto {

		private String name;

		public UserDto() {
		}

		public UserDto(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			return Objects.hash(name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			} else if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			UserDto other = (UserDto) obj;
			return Objects.equals(name, other.name);
		}
	}
}
