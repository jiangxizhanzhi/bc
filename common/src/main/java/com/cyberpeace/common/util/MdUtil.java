package com.cyberpeace.common.util;

import java.io.*;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * markdown操作工具类
 */
@Slf4j
public class MdUtil {

	private MdUtil() {}

	/**
	 * 解析markdown文件 返回json数据
	 * @param file markdown文件 不做文件存在判断 请在业务代码中判断
	 * @return
	 */
	public static JSONObject parseJsonMarkdown(File file) {

		// 获取markdown行数据
		List<String> contentList = FileUtil.listContent(file);

		// 新建json对象存储数据
		JSONObject valueObj = new JSONObject();

		// 遍历content数据
		String key = "";
		Integer startIndex = -1;
		for (int index = 0; index < contentList.size(); index++) {

			if (contentList.get(index).matches(PatternConstant.MARKDOWN_TITLE)) {
				// 数据校验
				key = validateMarkdownContent(contentList.get(index), file.getAbsolutePath());

				// 标题数据获取
				if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinList(contentList, startIndex, index);
					valueObj.put(key, value);
				}
				startIndex = index + CommonConstant.ONE;
			}

			if (contentList.get(index).matches(PatternConstant.MARKDOWN_TAG)) {

				// 标签数据获取
				if (StringUtils.isNotBlank(key) && startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinList(contentList, startIndex, index);
					valueObj.put(key, value);
				}
				startIndex = index + CommonConstant.ONE;

				// 数据校验
				key = validateMarkdownContent(contentList.get(index), file.getAbsolutePath());
			}

			if (index + 1 == contentList.size()) {
				String value = ListUtil.joinList(contentList, startIndex, contentList.size());
				valueObj.put(key, value);
			}
		}

		return valueObj;
	}

	/**
	 * 校验行内容
	 * @param lineContent
	 */
	private static String validateMarkdownContent(String lineContent, String path) {
		String[] titleArray = lineContent.split(" ");
		if (titleArray.length < CommonConstant.TWO) {
			log.error("parseJsonMarkdown: markdown_path=[{}]", path);
			throw new BusinessException(ErrorCode.FILE_CONTENT_INVALID);
		}

		return titleArray[1].trim();
	}

	public static void main(String[] args) {
		filterCodeContent("# <center>其他类型加密</center>\n" + "\n" + "## 培根密码\n" + "\n" + "### 原理\n"
				+ "\n" + "培根密码使用两种不同的字体，代表 A 和 B，结合加密表进行加解密。\n" + "\n"
				+ "| a    | AAAAA | g    | AABBA | n    | ABBAA | t    | BAABA |\n"
				+ "| :--- | :---- | :--- | :---- | :--- | :---- | :--- | :---- |\n"
				+ "| b    | AAAAB | h    | AABBB | o    | ABBAB | u-v  | BAABB |\n"
				+ "| c    | AAABA | i-j  | ABAAA | p    | ABBBA | w    | BABAA |\n"
				+ "| d    | AAABB | k    | ABAAB | q    | ABBBB | x    | BABAB |\n"
				+ "| e    | AABAA | l    | ABABA | r    | BAAAA | y    | BABBA |\n"
				+ "| f    | AABAB | m    | ABABB | s    | BAAAB | z    | BABBB |\n" + "\n"
				+ "上面的是常用的加密表。还有另外的一种加密表，可认为是将 26 个字母从 0 到 25 排序，以二进制表示，A 代表 0，B 代表 1。\n" + "\n"
				+ "下面这一段内容就是明文 steganography 加密后的内容，正常字体是 A，粗体是 B：\n" + "\n"
				+ "**T**o en**co**de **a** mes**s**age e**ac**h letter **of** the **pl**a**i**nt**ex**t **i**s replaced b**y a g**rou**p of f**i**ve** of **th**e lett**ers** **'A'** o**r 'B'**.\n"
				+ "\n" + "可以看到，培根密码主要有以下特点\n" + "\n" + "- 只有两种字符\n" + "- 每一段的长度为 5\n"
				+ "- 加密内容会有特殊的字体之分，亦或者大小写之分。\n" + "\n" + "### 工具\n" + "\n" + "- 培根密码在线解密工具\n" + "\n"
				+ "## 栅栏密码\n" + "\n" + "### 原理\n" + "\n"
				+ "栅栏密码把要加密的明文分成 N 个一组，然后把每组的第 1 个字连起来，形成一段无规律的话。这里给出一个例子：\n" + "\n" + "```\n"
				+ "明文：THERE IS A CIPHER\n" + "```\n" + "\n" + "去掉空格后变为：\n" + "\n" + "```\n"
				+ "THEREISACIPHER\n" + "```\n" + "\n" + "两个一组，分成若干栏得到：\n" + "\n" + "```\n"
				+ "TH ER EI SA CI PH ER\n" + "```\n" + "\n" + "先取出第一个字母，再取出第二个字母：\n" + "\n"
				+ "```\n" + "TEESCPE\n" + "HRIAIHR\n" + "```\n" + "\n" + "连在一起就是：\n" + "\n"
				+ "```\n" + "TEESCPEHRIAIHR\n" + "```\n" + "\n" + "上述明文也可以分为2栏：\n" + "\n" + "```\n"
				+ "THEREIS ACIPHER\n" + "```\n" + "\n" + "组合得到密文：\n" + "\n" + "```\n"
				+ "TAHCEIRPEHIESR\n" + "```\n" + "\n" + "## 曲路密码\n" + "\n" + "### 原理\n" + "\n"
				+ "曲路密码（Curve Cipher）是一种换位密码，需要事先双方约定密钥（也就是曲路路径）。下面给出一个例子：\n" + "\n" + "```\n"
				+ "明文：The quick brown fox jumps over the lazy dog\n" + "```\n" + "\n"
				+ "填入 5 行 7 列表（事先约定填充的行列数）\n" + "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/1.png)\n"
				+ "\n" + "加密的回路线（事先约定填充的行列数）\n" + "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/2.png)\n"
				+ "\n" + "```\n" + "密文：gesfc inpho dtmwu qoury zejre hbxva lookT\n" + "```\n" + "\n"
				+ "## 列移位加密\n" + "\n" + "### 原理\n" + "\n"
				+ "列移位密码（Columnar Transposition Cipher）是一种比较简单，易于实现的换位密码，通过一个简单的规则将明文打乱混合成密文。下面给出一个例子。\n"
				+ "\n" + "我们以明文 `The quick brown fox jumps over the lazy dog`，密钥 `how are u` 为例：\n"
				+ "\n" + "将明文填入 5 行 7 列表（事先约定填充的行列数，如果明文不能填充完表格可以约定使用某个字母进行填充）\n" + "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/3.png)\n"
				+ "\n"
				+ "密钥： `how are u`，按 `how are u` 在字母表中的出现的先后顺序进行编号，我们就有 a 为 1，e 为 2，h 为 3，o 为 4，r 为 5，u 为 6，w 为 7，所以先写出 a 列，其次 e 列，以此类推写出的结果便是密文：\n"
				+ "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/4.png)\n"
				+ "\n" + "密文： `qoury inpho Tkool hbxva uwmtd cfseg erjez`\n" + "\n"
				+ "## 01248 密码\n" + "\n" + "### 原理\n" + "\n"
				+ "该密码又称为云影密码，使用 0，1，2，4，8 四个数字，其中 0 用来表示间隔，其他数字以加法可以表示出 如：28=10，124=7，18=9，再用 1->26 表示 A->Z。\n"
				+ "\n" + "可以看出该密码有以下特点：\n" + "\n" + "- 只有 0，1，2，4，8\n" + "\n" + "### 例子\n" + "\n"
				+ "这里我们以CFF 2016 影之密码为例进行介绍，题目：\n" + "\n" + "> 8842101220480224404014224202480122\n"
				+ "\n" + "我们按照0来进行分割，如下\n" + "\n" + "| 内容   | 数字           | 字符 |\n"
				+ "| :----- | :------------- | :--- |\n" + "| 88421  | 8+8+4+2+1=23   | W    |\n"
				+ "| 122    | 1+2+2=5        | E    |\n" + "| 48     | 4+8=12         | L    |\n"
				+ "| 2244   | 2+2+4+4=12     | L    |\n" + "| 4      | 4              | D    |\n"
				+ "| 142242 | 1+4+2+2+4+2=15 | O    |\n" + "| 248    | 2+4+8=14       | N    |\n"
				+ "| 122    | 1+2+2=5        | E    |\n" + "\n" + "所以最后的 flag 为 WELLDONE。\n" + "\n"
				+ "## JSFuck\n" + "\n" + "### 原理\n" + "\n"
				+ "JSFuck 可以只用 6 个字符 `[]()!+` 来编写 JavaScript 程序。比如我们想用 JSFuck 来实现 `alert(1)` 代码如下\n"
				+ "\n" + "```\n"
				+ "[][(![]+[])[+[[+[]]]]+(/cpms/[][[]]+[])[+[[!+[]+!+[]+!+[]+!+[]+!+[]]]]+(![]+[])[+[[!+[]+!+[]]]]+(!![]+[])[+[[+[]]]]+(!![]+[])[+[[!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+!+[]]]]][([][(![]+[])[+[[+[]]]]+(/cpms/[][[]]+[])[+[[!+[]+!+[]+!+[]+!+[]+!+[]]]]+(![]+[])[+[[!+[]+!+[]]]]+(!![]+[])[+[[+[]]]]+(!![]+[])[+[[!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+!+[]]]]]+[])[+[[!+[]+!+[]+!+[]]]]+([][(![]+[])[+[[+[]]]]+(/cpms/[][[]]+[])[+[[!+[]+!+[]+!+[]+!+[]+!+[]]]]+(![]+[])[+[[!+[]+!+[]]]]+(!![]+[])[+[[+[]]]]+(!![]+[])[+[[!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+!+[]]]]]+[])[+[[!+[]+!+[]+!+[]+!+[]+!+[]+!+[]]]]+(/cpms/[][[]]+[])[+[[+!+[]]]]+(![]+[])[+[[!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+[]]]]+(!![]+[])[+[[+!+[]]]]+(/cpms/[][[]]+[])[+[[+[]]]]+([][(![]+[])[+[[+[]]]]+(/cpms/[][[]]+[])[+[[!+[]+!+[]+!+[]+!+[]+!+[]]]]+(![]+[])[+[[!+[]+!+[]]]]+(!![]+[])[+[[+[]]]]+(!![]+[])[+[[!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+!+[]]]]]+[])[+[[!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+[]]]]+([][(![]+[])[+[[+[]]]]+(/cpms/[][[]]+[])[+[[!+[]+!+[]+!+[]+!+[]+!+[]]]]+(![]+[])[+[[!+[]+!+[]]]]+(!![]+[])[+[[+[]]]]+(!![]+[])[+[[!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+!+[]]]]]+[])[+[[!+[]+!+[]+!+[]+!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+!+[]]]]]((![]+[])[+[[+!+[]]]]+(![]+[])[+[[!+[]+!+[]]]]+(!![]+[])[+[[!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+!+[]]]]+(!![]+[])[+[[+[]]]]+([][(![]+[])[+[[+[]]]]+(/cpms/[][[]]+[])[+[[!+[]+!+[]+!+[]+!+[]+!+[]]]]+(![]+[])[+[[!+[]+!+[]]]]+(!![]+[])[+[[+[]]]]+(!![]+[])[+[[!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+!+[]]]]]+[])[+[[+!+[]]]+[[!+[]+!+[]+!+[]+!+[]+!+[]]]]+[+!+[]]+([][(![]+[])[+[[+[]]]]+(/cpms/[][[]]+[])[+[[!+[]+!+[]+!+[]+!+[]+!+[]]]]+(![]+[])[+[[!+[]+!+[]]]]+(!![]+[])[+[[+[]]]]+(!![]+[])[+[[!+[]+!+[]+!+[]]]]+(!![]+[])[+[[+!+[]]]]]+[])[+[[+!+[]]]+[[!+[]+!+[]+!+[]+!+[]+!+[]+!+[]]]])()\n"
				+ "```\n" + "\n" + "其他一些基本的表达：\n" + "\n" + "```\n" + "false       =>  ![]\n"
				+ "true        =>  !![]\n" + "undefined   =>  [][[]]\n" + "NaN         =>  +[![]]\n"
				+ "0           =>  +[]\n" + "1           =>  +!+[]\n"
				+ "2           =>  !+[]+!+[]\n" + "10          =>  [+!+[]]+[+[]]\n"
				+ "Array       =>  []\n" + "Number      =>  +[]\n" + "String      =>  []+[]\n"
				+ "Boolean     =>  ![]\n" + "Function    =>  [][\"filter\"]\n"
				+ "eval        =>  [][\"filter\"][\"constructor\"]( CODE )()\n"
				+ "window      =>  [][\"filter\"][\"constructor\"](\"return this\")()\n" + "```\n"
				+ "\n" + "### 工具\n" + "\n" + "- JSFuck 在线加密网站\n" + "\n" + "## BrainFuck\n" + "\n"
				+ "### 原理\n" + "\n"
				+ "Brainfuck，是一种极小化的计算机语言，它是由 Urban Müller 在 1993 年创建的。我们举一个例子，如果我们想要一个在屏幕上打印Hello World！，那么对应的程序如下。对于其中的原理，感兴趣的可以自行网上搜索。\n"
				+ "\n" + "```\n" + "++++++++++[>+++++++>++++++++++>+++>+<<<<-]\n"
				+ ">++.>+.+++++++..+++.>++.<<+++++++++++++++.\n" + ">.+++.------.--------.>+.>.\n"
				+ "```\n" + "\n" + "与其对应的还有 ook。\n" + "\n" + "### 工具\n" + "\n"
				+ "- Brainfuck在线解密工具\n" + "\n" + "## 猪圈密码\n" + "\n" + "### 原理\n" + "\n"
				+ "猪圈密码是一种以格子为基础的简单替代式密码，格子如下\n" + "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/5.png)\n"
				+ "\n" + "我们举一个例子，如明文为 `X marks the spot` ，那么密文如下\n" + "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/6.png)\n"
				+ "\n" + "## 舞动的小人密码\n" + "\n" + "### 原理\n" + "\n"
				+ "这种密码出自于福尔摩斯探案集。每一个跳舞的小人实际上对应的是英文二十六个字母中的一个，而小人手中的旗子则表明该字母是单词的最后一个字母，如果仅仅是一个单词而不是句子，或者是句子中最后的一个单词，则单词中最后一个字母不必举旗。\n"
				+ "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/7.png)\n"
				+ "\n" + "## 键盘密码\n" + "\n" + "所谓键盘密码，就是采用手机键盘或者电脑键盘进行加密。\n" + "\n" + "### 手机键盘密码\n"
				+ "\n"
				+ "手机键盘加密方式，是每个数字键上有 3-4 个字母，用两位数字来表示字母，例如：ru 用手机键盘表示就是：7382，那么这里就可以知道了，手机键盘加密方式不可能用 1 开头，第二位数字不可能超过 4，解密的时候参考此\n"
				+ "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/8.png)\n"
				+ "\n"
				+ "关于手机键盘加密还有另一种方式，就是「音的」式（这一点可能根据手机的不同会有所不同），具体参照手机键盘来打，例如：「数字」表示出来就是：748 94。在手机键盘上面按下这几个数，就会出：「数字」的拼音。\n"
				+ "\n" + "### 电脑键盘棋盘\n" + "\n" + "电脑键盘棋盘加密，利用了电脑的棋盘方阵。\n" + "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/9.png)\n"
				+ "\n" + "### 电脑键盘坐标\n" + "\n"
				+ "电脑键盘坐标加密，利用键盘上面的字母行和数字行来加密，例：bye 用电脑键盘 XY 表示就是：351613\n" + "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/10.png)\n"
				+ "\n" + "### 电脑键盘 QWE\n" + "\n" + "电脑键盘 QWE 加密法，就是用字母表替换键盘上面的排列顺序。\n" + "\n"
				+ "![](/cpms/oj/course/isccrypto/handout/68461f676d7246d29703db4c28122fd1/img/11.png)\n"
				+ "\n" + "### 键盘布局加密\n" + "\n" + "简单地说就是根据给定的字符在键盘上的样子来进行加密。\n" + "\n"
				+ "### 0CTF 2014 classic\n" + "\n"
				+ "> 小丁丁发现自己置身于一个诡异的房间，面前只有一扇刻着奇怪字符的门。 他发现门边上还有一道密码锁，似乎要输入密码才能开门。。4esxcft5 rdcvgt 6tfc78uhg 098ukmnb\n"
				+ "\n" + "发现这么乱，还同时包括数字和字母猜想可能是键盘密码，试着在键盘上按照字母顺序描绘一下，可得到0ops字样，猜测就是flag了。\n" + "\n"
				+ "### 2017年xman选拔赛——一二三，木头人\n" + "\n" + "> 我数123木头人，再不行动就要被扣分。\n" + ">\n"
				+ "> 23731263111628163518122316391715262121\n" + ">\n" + "> 密码格式xman{flag}\n" + "\n"
				+ "题目中有很明显的提示123，那么就自然需要联想到键盘密码中电脑键盘坐标密码，可以发现前几个数字第二个数字都是1-3范围内的，也验证了我们的猜测。于是\n"
				+ "\n" + "> 23-x\n" + ">\n" + "> 73-m\n" + ">\n" + "> 12-a\n" + ">\n" + "> 63-n\n"
				+ ">\n" + "> 11-q\n" + "\n"
				+ "不对呀，密码格式是 `xman{`，第四个字符是 `{`，于是看了看 `{` 的位置，其并没有对应的横坐标，但是如果我们手动把它视为 11 的话，那么111就是 `{`。然后依次往后推，发现确实可行，，最后再把 121 视为 `}` 即可得到 flag。\n"
				+ "\n" + "```\n" + "xman{hintisenough}\n" + "```\n" + "\n"
				+ "从这里我们可以看出，我们还是要注意迁移性，不能单纯地照搬一些已有的知识。\n");
	}

	/**
	 * 解读markdown文件内容 替换文件路径 由相对路径替换为绝对路径 用于后续文件复制然后替换
	 * @param markdownPath
	 * @return
	 */
	public static String parseMarkdown(String markdownPath) {

		// 获取当前文件夹
		String currentDir = new File(markdownPath).getParentFile().getAbsolutePath();

		// 获取markdown文件内容
		File markdownFile = new File(markdownPath);
		String content = FileUtil.getContent(markdownFile);
		String withOutCode = getContentWithOutCode(markdownFile, CommonConstant.ONE);

		// 匹配文件路径
		List<String> matchStrList = LocalStringUtil.getMatchUrlStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE_MD_URL);

		// 匹配文件src地址
		List<String> matchSrcStrList = LocalStringUtil.getMatchUrlStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE_SRC_URL);

		matchStrList.addAll(matchSrcStrList);


		// 文件路径替换
		for (String imagePath : matchStrList) {
//			String imagePath = LocalStringUtil.getFirstMatchStr(matchStr,
//					PatternConstant.MARKDOWN_IMAGE_URL);
			// 部分markdown文件中文件路径 存在./xxx/xxx.png 所以要把逗点给去掉
			String relativePath = imagePath;
			if (imagePath.startsWith(CommonConstant.PERIOD)) {
				relativePath = imagePath.replaceFirst(CommonConstant.PERIOD,
						CommonConstant.EMPTY_STRING);
			}

			File replaceFile = new File(currentDir, relativePath);
			if (!replaceFile.exists()) {
				log.error("File not exist! file_path=[{}]", replaceFile.getAbsolutePath());
				throw new BusinessException(ErrorCode.FILE_NOT_EXIST);
			}
			content = content.replace(imagePath, replaceFile.getAbsolutePath());
		}

		return content;
	}

	/**
	 * markdown内容过滤掉代码块
	 * @return
	 */
	public static String filterCodeContent(String content) {
		boolean coding = false;
		StringBuilder builder = new StringBuilder();
		String[] contentList = StringUtils.split(content, System.lineSeparator());
		for (String contentStr : contentList) {
			coding = append(contentStr, coding, builder, CommonConstant.ONE);
			builder.append(System.lineSeparator());
		}

		return builder.toString();
	}

	private static boolean append(String content, boolean coding, StringBuilder builder, int type) {
		// 如果开始和结尾在一行里 直接跳过此行
		boolean singleLine = content.trim().length() >= CommonConstant.FOUR
				&& content.trim().startsWith(CommonConstant.MD_CODING_BLOCK)
				&& content.trim().endsWith(CommonConstant.MD_CODING_BLOCK)
				&& CommonConstant.ONE.intValue() == type;
		if (singleLine) {
			return coding;
		}

		// 如果此行开始为代码块标识符 则对下面内容进行略过
		boolean omit = (content.trim().startsWith(CommonConstant.MD_CODING_BLOCK)
				|| content.trim().endsWith(CommonConstant.MD_CODING_BLOCK))
				&& CommonConstant.ONE.intValue() == type;
		if (omit) {
			coding = !coding;
		} else {
			if (!coding) {
				String compareContent = content;
				//当前行内存在字符 ` 时，开始裁剪
				while (content.contains(CommonConstant.MD_CODING_BLOCK_ONE)) {
					int firstIndex = content.indexOf(CommonConstant.MD_CODING_BLOCK_ONE, 0);
					compareContent = compareContent.replaceFirst(CommonConstant.MD_CODING_BLOCK_ONE, "");
					//存在两个`符号时，进行裁剪，否则跳出循环
					if (compareContent.contains(CommonConstant.MD_CODING_BLOCK_ONE)) {
						int secondIndex = compareContent.indexOf(CommonConstant.MD_CODING_BLOCK_ONE, 0);
						content = content.replace(content.substring(firstIndex,secondIndex + 2),"");
						compareContent = content;
					}else {
						break;
					}
				}
				builder.append(content + System.lineSeparator());
			}
		}

		return coding;
	}

	/**
	 * 读取md文件内容 不包含代码块
	 * @param file
	 * @param type 0=读取全部内容 1=过滤掉代码块
	 * @return
	 */
	public static String getContentWithOutCode(File file, int type) {

		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		boolean coding = false;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String content;

			while ((content = br.readLine()) != null) {
				coding = append(content, coding, builder, type);
			}

			return builder.toString();
		} catch (Exception e) {
			// 文件读取失败
			log.error("file read failed! filePath={} error:", file.getAbsolutePath(), e);
			throw new BusinessException(ErrorCode.FILE_CONTENT_INVALID);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// 不做任何处理
			} catch (NullPointerException e) {
				// 对象未创建空指针异常捕获，不做任何处理
			}
		}
	}

	public static File saveAsFileWriter(String content, String savefile) {
		File file = new File(savefile);
		try {
			if (file.exists()) {
				log.info("saveAsFileWriter file already exists: [{}]", savefile);
			} else {
				boolean newFileFlag = file.createNewFile();
				if (!newFileFlag) {
					log.info("saveAsFileWriter failed to create new file: [{}]", savefile);
				}
			}

			try (FileWriter fileWriter = new FileWriter(file)) {
				fileWriter.write(content);
			}

		}catch (IOException ex){
			ex.printStackTrace();
		}

		return file;
	}

	/**
	 * 解读markdown文件内容 替换文件路径 由相对路径替换为绝对路径 用于后续文件复制然后替换
	 * @param mdFile md文件
	 * @param content md内容
	 * @return
	 */
	public static String dumpMarkdownImages(File mdFile, String content) {
		String img = new File(mdFile.getParentFile().getAbsolutePath(), "img").getAbsolutePath();
		FileUtil.mkdirs(img);

		// 匹配文件路径
		String withOutCode = filterCodeContent(content);
		List<String> matchStrList = LocalStringUtil.getMatchUrlStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE_MD_URL);

		// 匹配文件src地址
		List<String> matchSrcStrList = LocalStringUtil.getMatchUrlStr(withOutCode,
				PatternConstant.MARKDOWN_IMAGE_SRC_URL);
		matchStrList.addAll(matchSrcStrList);

		// markdown中引用重复文件, 避免String.replace替换多次, 转为Set（zentao 9894）
		Set<String> matchStrSet = Sets.newHashSet(matchStrList);

		// 文件路径替换
		for (String imagePath : matchStrSet) {
			// 部分markdown文件中文件路径 存在./xxx/xxx.png 所以要把逗点给去掉
			String realPath = getRealPath(imagePath);
			File source = new File(realPath);
			if (!source.exists()) {
				log.error("source is not   exist! realPath = [{}]",realPath);
				throw new BusinessException(ErrorCode.FILE_ERROR_FAIL, realPath);
			}

			File dist = new File(img, source.getName());
			if (!dist.exists()) {
				FileUtil.copyFile(source.getAbsolutePath(), dist.getAbsolutePath(), false);
			}

			content = content.replace(imagePath, "img/" + dist.getName());
		}

		return content;
	}

	private static String getRealPath(String fileUrl) {

		String localPrefix = ConfigUtil.getContextProperty("cr.local.file.prefix");
		String localParentPath = ConfigUtil.getContextProperty("cr.local.upload.path");

		if (StringUtils.isNotBlank(fileUrl)) {
			if (fileUrl.startsWith(localParentPath)) {
				return fileUrl;
			}

			if (fileUrl.startsWith(localPrefix)) {
				return fileUrl.replace(localPrefix, localParentPath + File.separator);
			}

			return localParentPath + File.separator + fileUrl;

		}

		return fileUrl;
	}

	/**
	 * md内容写入文件 对应的文件写入md同级的img目录
	 * @param file
	 * @param content
	 */
	public static void writeMdToFile(File file, String content) {
		String mdContent = dumpMarkdownImages(file, content);
		FileUtil.writeToDestFile(mdContent, file.getAbsolutePath());
	}
}
