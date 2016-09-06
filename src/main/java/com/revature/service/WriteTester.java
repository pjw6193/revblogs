package com.revature.service;

import java.io.IOException;
import java.util.Date;

import com.revature.beans.Blog;
import com.revature.beans.User;

public class WriteTester {
	
	private static HtmlWriter testWriter;

	private WriteTester(){
		throw new IllegalAccessError("Utility class");
	}
	
	public static void main(String[] args) throws IOException {
		Blog testBlog = new Blog();
		testBlog.setBlogTitle("The Sound of Silence");
		testBlog.setBlogContent("<p>Hello darkness my old friend</p>"
				+ "<p>I've come to talk with you again</p>"
				+ "<p>Because a vision softly creeping</p>"
				+ "<p>Left its seeds while I was sleeping</p>"
				+ "<p>And the vision that was planted in my brain</p>"
				+ "<p>Still remains</p>"
				+ "<p>Within the sound of silence</p>");
		testBlog.setPublishDate(new Date());
		User author = new User();
		author.setFirstName("Simon");
		author.setLastName("& Garfunkel");
		author.setDescription("Simon and Garfunkel are Paul Simon and Art Garfunkel. They are musicians who one day decided to start recording together. <i>The Sound of Silence</i> might be their most well-known song.");
		testWriter = new HtmlWriter(testBlog, author, WriteTester.class.getClassLoader().getResourceAsStream("template.html"));
		try {
			testWriter.render();
		} catch (IOException e) {
			
			// Should be logged auto-magic-lly with AOP
		}
	}

}
