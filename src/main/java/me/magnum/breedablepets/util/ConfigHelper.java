package me.magnum.breedablepets.util;

import lombok.NoArgsConstructor;
import me.magnum.Breedable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@NoArgsConstructor
public class ConfigHelper {

	private String[] header;

	public List<String> framedHeader (String... header) {
		List <String> stringList = new ArrayList<>();
		String border = "# +----------------------------------------------------+ #";
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

			builder.append("# < ").append(finalLine.toString()).append(" > #");
			stringList.add(builder.toString());
		}
		stringList.add(border);
		return stringList;
	}

	private String prepareConfigString (String configString) {

		int lastLine = 0;
		int headerLine = 0;

		String[] lines = configString.split("\n");
		StringBuilder config = new StringBuilder();

		for (String line : lines) {
			// Hard coded plugin instance - TODO Change to field
			if (line.startsWith(Breedable.getPlugin().getName() + "_COMMENT")) {
				String comment = "#" + line.trim().substring(line.indexOf(":") + 1);

				if (comment.startsWith("# +-")) {

					/*
					 * If header line = 0 then it is
					 * header start, if it's equal
					 * to 1 it's the end of header
					 */

					if (headerLine == 0) {
						config.append(comment).append("\n");

						lastLine = 0;
						headerLine = 1;

					}
					else {
						config.append(comment).append("\n\n");

						lastLine = 0;
						headerLine = 0;

					}

				}
				else {

					/*
					 * Last line = 0 - Comment
					 * Last line = 1 - Normal path
					 */

					String normalComment;

					if (comment.startsWith("# ' ")) {
						normalComment = comment.substring(0, comment.length() - 1).replaceFirst("# ' ", "# ");
					}
					else {
						normalComment = comment;
					}

					if (lastLine == 0) {
						config.append(normalComment).append("\n");
					}
					else {
						config.append("\n").append(normalComment).append("\n");
					}

					lastLine = 0;

				}

			}
			else {
				config.append(line).append("\n");
				lastLine = 1;
			}

		}

		return config.toString();

	}

}
