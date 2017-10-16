public final class StringUtil {
	/**
	 * <p>
	 * Returns the empty String "".
	 * </p>
	 */
	public static final String EMPTY = "";

	/**
	 * <p>
	 * Returns the new line String "\r\n".
	 * </p>
	 */
	public static final String NEWLINE = "\r\n";

	/**
	 * <p>
	 * Determines whether a string is null or empty.
	 * </p>
	 *
	 * @param input
	 * @return true if the String is null or empty; false otherwise.
	 */
	public static boolean isNullOrEmpty(String input) {
		return input == null || EMPTY.equals(input);
	}

	/**
	 * <p>
	 * Compares two Strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.compare(null, null) = 0
	 * StringUtil.compare(null, "...") = -1
	 * StringUtil.compare("...", null) = 1
	 * </pre>
	 *
	 * @param str1
	 * @param str2
	 * @param ignoreCase
	 * @return 1 if str1 > str2, -1 if str1 < str2, 0 if str1 = str2
	 */
	public static int compare(String str1, String str2, boolean ignoreCase) {
		int result = 0;
		if (str1 != null) {
			if (str2 != null) {
				if (ignoreCase) {
					result = str1.compareToIgnoreCase(str2);
				} else {
					result = str1.compareTo(str2);
				}
			} else {
				result = 1;
			}
		} else {
			if (str2 != null) {
				result = -1;
			}
		}
		return result;
	}

}