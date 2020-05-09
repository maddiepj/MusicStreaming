public class JPages
{
		Long guid;
		Long size;
		String creationTS;
		String readTS;
		String writeTS;
		int referenceCount;
		

		public JPages(Long guid, Long size, String creationTS, String readTS, String writeTS, int referenceCount) {

			this.guid = guid;
			this.size = size;
			this.creationTS = creationTS;
			this.readTS = readTS;
			this.writeTS = writeTS;
			this.referenceCount = referenceCount;
		}

		// getters
		public Long getSize() {
			return this.size;
		}

		public Long getGuid() {
			return this.guid;
		}

		public String getCreationTS() {
			return this.creationTS;
		}

		public int getReferenceCount() {
			return this.referenceCount;
		}

		// setters
		public void setSize(Long size) {
			this.size = size;
		}

		public void setGuid(Long guid) {
			this.guid = guid;
		}

		public void setCreationTS(String creationTS) {
			this.creationTS = creationTS;
		}

		public void setReadTS(String readTS) {
			this.readTS = readTS;
		}

		public void setWriteTS(String writeTS) {
			this.writeTS = writeTS;
		}

		public void setReferenceCount(int referenceCount) {
			this.referenceCount = referenceCount;
		}
};