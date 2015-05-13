
import org.apache.commons.io.IOUtils;
import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.RegexStoryParser;

import java.io.IOException;
import java.io.StringReader;


public class RegexStoryRemovingCommentsParser extends RegexStoryParser {

	@Override
	public Story parseStory(String storyAsText, String storyPath) {
		super.parseStory(removeCommentsFromStoryFile(storyAsText), storyPath);
	}

	private String removeCommentsFromStoryFile(String storyText) {
		try {
			StringBuilder storyTextWithoutComments = new StringBuilder();
			for (String line : IOUtils.readLines(new StringReader(storyText))) {
				if (lineIsNotComment(line)) {
					storyTextWithoutComments.append(line).append(System.getProperty("line.separator"));
				}
			}
			return storyTextWithoutComments.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean lineIsNotComment(String line) {
		return !line.startsWith("|--") && !line.startsWith("!--");
	}
}
