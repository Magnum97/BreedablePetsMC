package me.magnum.breedablepets.util;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class YamlHelper {

	private String[] header;

	public List <String> framedHeader (String... header) {
		List <String> stringList = new ArrayList <>();
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
}
