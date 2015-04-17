package base.util.character;

public class StringUtil {

	/**
	 * java中判断字段真实长度（中文2个字符，英文1个字符）的方法 1、判断字符串是否为连续的中文字符(不包含英文及其他任何符号和数字)：
	 * Regex.IsMatch("中文","^[/u4e00-/u9fa5]")；
	 * 2、判断字符串是否为中文字符串(仅不包含英文但可以包含其他符号及数字)： ！Regex.IsMatch("中文",@"[a-zA-Z]")；
	 * 
	 * @param value
	 * @return
	 */
	public static int stringLength(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}
}
