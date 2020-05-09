import java.time.LocalDateTime;
import java.util.ArrayList;

public class JFile {
		String name;
		Long size;
		String creationTS;
		String readTS;
		String writeTS;
		int referenceCount;
		int numOfPages;
		int maxPageSize;
		ArrayList<JPages> pages;

		public JFile() {
			this.size = new Long(0);
			this.numOfPages = 0;
			this.referenceCount = 0;
			this.creationTS = LocalDateTime.now().toString();
			this.readTS = "0";
			this.writeTS = "0";
			this.maxPageSize = 0;
			this.pages = new ArrayList<JPages>();
		}

		public void addPageInfo(Long guid, Long size, String creationTS, String readTS, String writeTS,
				int referenceCount) {
			JPages page = new JPages(guid, size, creationTS, readTS, writeTS, referenceCount);
			pages.add(page);
		}

		public int getMaxPageSize() {
			return this.maxPageSize;
		}

		public int getReferenceCount() {
			return this.referenceCount;
		}

		public int getNumOfPages() {
			return this.numOfPages;
		}

		public Long getSize() {
			return this.size;
		}

		public ArrayList<JPages> getPages() {
			return pages;
		}

		public String getCreationTS() {
			return creationTS;
		}

		public String getName() {
			return this.name;
		}

		// setters
		public void setMaxPageSize(int maxPageSize) {
			this.maxPageSize = maxPageSize;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setSize(Long size) {
			this.size = size;
		}

		public void addSize(Long size) {
			this.size += size;
		}

		public void setReferenceCount(int referenceCount) {
			this.referenceCount = referenceCount;
		}

		public void setNumOfPages(int numOfPages) {
			this.numOfPages = numOfPages;
		}

		public void addNumOfPages(int numOfPages) {
			this.numOfPages += numOfPages;
		}

		public void setCreationTS(String time) {
			this.creationTS = time;
		}

		public void setReadTS(String time) {
			this.readTS = time;
		}

		public void setWriteTS(String time) {
			this.writeTS = time;
		}
};