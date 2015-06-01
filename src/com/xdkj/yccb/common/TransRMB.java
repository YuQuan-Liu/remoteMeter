package com.xdkj.yccb.common;

public class TransRMB{

	
	
	private static String splitNum(String s) {
		// 如果传入的是空串则继续返回空串
		if("".equals(s)) {
			return "";
		}
		// 以小数点为界分割这个字符串
		int index = s.indexOf(".");
		// 截取并转换这个数的整数部分
		String intOnly = s.substring(0, index);
		String part1 = numFormat(1, intOnly);
		// 截取并转换这个数的小数部分
		String smallOnly = s.substring(index + 1);
		String part2 = numFormat(2, smallOnly);
		// 把转换好了的整数部分和小数部分重新拼凑一个新的字符串
		String newS = part1 + part2;
		return newS;
	}
	
	private static String numFormat(int flag, String ss) {
		String s = ss;
		if(flag == 2){
			if(s.length() >= 2){
				s = ss.substring(0, 2);
			}else{
				s = ss+"0";
			}
		}
		int sLength = s.length();
		// 货币大写形式
		String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
		// 货币单位
		String unit[] = {"元", "拾", "佰", "仟", "万", 
				// 拾万位到仟万位
				"拾", "佰", "仟",
				// 亿位到万亿位
				"亿", "拾", "佰", "仟", "万"};
		String small[] = {"分", "角"};
		// 用来存放转换后的新字符串
		String newS = "";
		// 逐位替换为中文大写形式
		for(int i = 0; i < sLength; i ++) {
			if(flag == 1) {
				// 转换整数部分为中文大写形式（带单位）
				newS = newS + bigLetter[s.charAt(i) - 48] + unit[sLength - i - 1];
			} else if(flag == 2) {
				// 转换小数部分（带单位）
				newS = newS + bigLetter[s.charAt(i) - 48] + small[sLength - i - 1];
			}
		}
		return newS;
	}
	
	private static String cleanZero(String s) {
		// 如果传入的是空串则继续返回空串
		if("".equals(s)) {
			return "";
		}
		// 如果用户开始输入了很多 0 去掉字符串前面多余的'零'，使其看上去更符合习惯
		while(s.charAt(0) == '零') {
			// 将字符串中的 "零" 和它对应的单位去掉
			s = s.substring(2);
			// 如果用户当初输入的时候只输入了 0，则只返回一个 "零"
			if(s.length() == 0) {
				return "零";
			}
		}
		// 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位
		/* 由于本人对算法的研究太菜了，只能用4个正则表达式去转换了，各位大虾别介意哈... */
		String regex1[] = {"零仟", "零佰", "零拾"};
		String regex2[] = {"零亿", "零万", "零元"};
		String regex3[] = {"亿", "万", "元"};
		String regex4[] = {"零角", "零分"};
		// 第一轮转换把 "零仟", 零佰","零拾"等字符串替换成一个"零"
		for(int i = 0; i < 3; i ++) {
			s = s.replaceAll(regex1[i], "零");
		}
		// 第二轮转换考虑 "零亿","零万","零元"等情况
		// "亿","万","元"这些单位有些情况是不能省的，需要保留下来
		for(int i = 0; i < 3; i ++) {
			// 当第一轮转换过后有可能有很多个零叠在一起
			// 要把很多个重复的零变成一个零
			s = s.replaceAll("零零零", "零");
			s = s.replaceAll("零零", "零");
			s = s.replaceAll(regex2[i], regex3[i]);
		}
		// 第三轮转换把"零角","零分"字符串省略
		for(int i = 0; i < 2; i ++) {
			s = s.replaceAll(regex4[i], "");
		}
		// 当"万"到"亿"之间全部是"零"的时候，忽略"亿万"单位，只保留一个"亿"
		s = s.replaceAll("亿万", "亿");
		return s;
	}
	
	public static String transform(String money){
		
		return cleanZero(splitNum(money));
	}
	
	public static void main(String[] args) {
		System.out.println(transform("14.111"));
		
	}
}
