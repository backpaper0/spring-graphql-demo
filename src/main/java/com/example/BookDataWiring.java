package com.example;

import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

import graphql.schema.idl.RuntimeWiring.Builder;

@Component
public class BookDataWiring implements RuntimeWiringBuilderCustomizer {

	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;

	public BookDataWiring(BookRepository bookRepository, AuthorRepository authorRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
	}

	@Override
	public void customize(Builder builder) {
		builder.type("Query",
				typeRuntimeWiringBuilder -> typeRuntimeWiringBuilder.dataFetcher("book", env -> {
					String isbn = env.getArgument("isbn");
					return bookRepository.findByIsbn(isbn);
				}));
		builder.type("Book",
				typeRuntimeWiringBuilder -> typeRuntimeWiringBuilder.dataFetcher("author", env -> {
					Book book = env.getSource();
					return authorRepository.findById(book.getAuthorId());
				}));
	}
}
