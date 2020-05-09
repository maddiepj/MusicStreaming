import java.util.ArrayList;
import java.util.List;

public class JFiles {
		List<JFile> file;

		public JFiles() {
			file = new ArrayList<JFile>();

		}

		// getters
		public JFile getFileJson(int index) {
			return file.get(index);
		}

		public int getSize() {
			return file.size();
		}

		// setters
		public void addFile(JFile fileJson) {
			file.add(fileJson);

		}
};