package me.magnum.breedablepets.util;

import de.leonhard.storage.Yaml;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class YamlHelper {

	private String[] header;
	private int commentsCounter;

	public List <String> framedHeader (String... header) {
		List <String> stringList = new ArrayList <>();
		String border = "# ┼─────────────────────────────────────────────────────┼ #";
		stringList.add(border);

		for (String line : header) {
			StringBuilder builder = new StringBuilder();
			if (line.length() > 50) {
				continue;
			}

			int length = (50 - line.length()) / 2;
			StringBuilder finalLine = new StringBuilder(line);

			for (int i = 0; i < length; i++) {
				finalLine.append(" ");
				finalLine.reverse();
				finalLine.append(" ");
				finalLine.reverse();
			}

			if (line.length() % 2 != 0) {
				finalLine.append(" ");
			}

			builder.append("# │").append(finalLine.toString()).append(" │#");
			stringList.add(builder.toString());
		}
		stringList.add(border);
		return stringList;
	}

	public void setComment (Yaml yaml, String path, Object value, String comment) {
		int comments = getCommentsNum(yaml.getFile());
		if (! yaml.contains(path)) {
			yaml.set(yaml.getName() + "_COMMENT_" + comments, " " + comment);
		}
		yaml.set(path, value);
	}

	/**
	 * Get comments from file
	 *
	 * @param file - File
	 * @return - Comments number
	 */
	private int getCommentsNum (File file) {

		if (! file.exists()) {
			return 0;
		}

		try {
			int comments = 0;
			String currentLine;

			BufferedReader reader = new BufferedReader(new FileReader(file));

			while ((currentLine = reader.readLine()) != null) {

				if (currentLine.startsWith("#")) {
					comments++;
				}

			}

			reader.close();
			return comments;

		}
		catch (IOException e) {
			e.printStackTrace();
			return 0;
		}

	}

}
