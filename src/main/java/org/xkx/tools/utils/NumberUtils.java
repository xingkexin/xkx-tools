package org.xkx.tools.utils;

public class NumberUtils {

	/**
	 * 将字符串按照进制规则转换成数字。
	 * @param str 字符串
	 * @param baseNum 进制规则，取值为：2、8、10、16
	 * @return
	 */
	public static long parseNumber(String str, int baseNum) {
		long num = 0;
		str = str.replaceAll("[ ,]", "");
		for(char c : str.toCharArray()) {
			num = num * baseNum + parseUnitNumber(c);
		}
		return num;
	}

	/**
	 * 将数字格式化为其他进制的字符串。
	 * @param num 待格式化的数字
	 * @param baseNum 进制规则
	 * @return
	 */
	public static String formatNumber(long num, int baseNum) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();

		while (num != 0) {
			sb.append(formatUnitNumber((int) (num%baseNum)));
			num = num/baseNum;
		}
		for(int i=sb.length()-1; i>=0; i--) {
			sb2.append(sb.charAt(i));
		}

		return sb2.toString();
	}

	/**
	 * 将字符串从一个进制转为另一个进制。
	 * @param str 字符串
	 * @param originalBaseNum 原始进制
	 * @param targetBaseNum 目标进制
	 * @return
	 */
	public static String convertNumber(String str, int originalBaseNum, int targetBaseNum) {
		return formatNumber(parseNumber(str, originalBaseNum), targetBaseNum);
	}

	public static String formatUnitNumber(int num) {
		if(num >= 16 || num < 0) return "";
		switch(num) {
			case 10: return "a";
			case 11: return "b";
			case 12: return "c";
			case 13: return "d";
			case 14: return "e";
			case 15: return "f";
			default: return num + "";
		}
	}

	public static int parseUnitNumber(char c) {
		switch(c) {
			case 'a': return 10;
			case 'b': return 11;
			case 'c': return 12;
			case 'd': return 13;
			case 'e': return 14;
			case 'f': return 15;
			default: return c - 48;
		}
	}

	public static void main(String[] args) {
		long num = 55296;
		System.out.println(formatNumber(num, 2));
		System.out.println(formatNumber(num, 8));
		System.out.println(formatNumber(num, 10));
		System.out.println(formatNumber(num, 16));
		System.out.println(parseNumber("111111111111111111111111 11111111", 2));
		System.out.println(parseNumber("37777777777", 8));
		System.out.println(parseNumber("4,294,967,295", 10));
		System.out.println(parseNumber("d800", 16));
		System.out.println(convertNumber("d800", 16, 2));
	}
}
