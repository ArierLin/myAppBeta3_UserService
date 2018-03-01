package com.jr.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 验证码
 */
public class VerifyCode {
	private int width = 70; // 宽
	private int height = 30; // 高
	private Color bgColor = new Color(240, 240, 240); // 背景颜色

	private StringBuilder code = new StringBuilder(); // 记录验证码，用于比较

	private final Random random = new Random();

	//验证码干扰线数量
	final int RANDCODE_LINECOUNT = ResourceUtil.getRandCodeLineCount();

	/**
	 * 干扰线的长度=1.414*LINE_WIDTH
	 */
	private static final int LINE_WIDTH = ResourceUtil.getRandCodeLineWidth();

	// 输出图象到页面
	public static void saveImage(BufferedImage img, OutputStream out) throws IOException {
		ImageIO.write(img, "JPEG", out);
	}

	// 用户调用这个方法获取图片
	public BufferedImage getImage() {
		/*
		 * 写什么字符：随机生成，范围：0-9、A-Z、a-z 字体：随机 字符颜色：随机
		 */
		BufferedImage img = createImage();
		Graphics g = img.getGraphics();
		// 画东西
		//获取随机字符
		String randomCode = this.exctractRandCode();
		for (int i = 0; i < randomCode.length(); i++) {
			code.append(randomCode.charAt(i)); // 记录生成的字符
			g.setColor(this.randomColor()); // 获取随机颜色
			g.setFont(this.randomFont()); // 获取随机字体
			//写字	设置字符，字符间距，上边距
			g.drawString(String.valueOf(randomCode.charAt(i)), width / 4 * i, height - 5);
		}
		this.drawLine(img); //添加干扰线

		return img;
	}

	//创建图片
	private BufferedImage createImage() {
		/*
		 * 1. 创建图片 2. 设置背景色
		 */
		// 创建图片
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 设置画笔颜色
		img.getGraphics().setColor(bgColor);
		// 填充一个与图片一样大小的矩形！即设置背景色！
		img.getGraphics().fillRect(0, 0, width, height);

		// 设定边框颜色
		//graphics.setColor(getRandColor(100, 200)); // ---2.这是以数字型来设置颜色，颜色模式是指使用三种基色：红、绿、蓝，通过三种颜色的调整得出其它各种颜色，这三种基色的值范围为0～255
		img.getGraphics().drawRect(0, 0, width - 1, height - 1);

		return img;
	}

	// 生成干扰线
	private void drawLine(BufferedImage img) {
		Graphics graphics = img.getGraphics();
		// Graphics2D g2d = (Graphics2D) img.getGraphics();
		// g2d.setStroke(new BasicStroke(1.5F)); //调整画笔粗细
		// 随机产生干扰线，使图象中的认证码不易被其它程序探测到
		for (int i = 0; i < RANDCODE_LINECOUNT; i++) {
			//干扰线随机颜色
			graphics.setColor(getRandColor(150, 200)); // ---3.

			final int x = random.nextInt(width - LINE_WIDTH - 1) + 1; // 保证画在边框之内
			final int y = random.nextInt(height - LINE_WIDTH - 1) + 1;
			final int xl = random.nextInt(LINE_WIDTH);
			final int yl = random.nextInt(LINE_WIDTH);
			graphics.drawLine(x, y, x + xl, y + yl);
		}
	}

	/**
	 * 得到随机颜色
	 * @return
	 */
	private Color randomColor() {
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);
		return new Color(r, g, b);
	}

	/**
	 * 描述：根据给定的数字生成不同的颜色
	 * @param //这是以数字型来设置颜色，颜色模式是指使用三种基色：红、绿、蓝，通过三种颜色的调整得出其它各种颜色，这三种基色的值范围为0～255
	 * @param //这是以数字型来设置颜色，颜色模式是指使用三种基色：红、绿、蓝，通过三种颜色的调整得出其它各种颜色，这三种基色的值范围为0～255
	 * @return 描述：返回颜色
	 */
	private Color getRandColor(int fc, int bc) { // 取得给定范围随机颜色
		final Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		final int r = fc + random.nextInt(bc - fc);
		final int g = fc + random.nextInt(bc - fc);
		final int b = fc + random.nextInt(bc - fc);

		return new Color(r, g, b);
	}

	/*
	 * 字体号、样式、字号
	 */
	private String[] fontNames = { "宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312" };
	private int[] fontSizes = { 24, 25, 26, 27, 28 };

	/**
	 * 得到随机字体
	 * @return
	 */
	private Font randomFont() {
		int index = random.nextInt(fontNames.length);
		String name = fontNames[index];

		int style = random.nextInt(4);

		index = random.nextInt(fontSizes.length);
		int size = fontSizes[index];
		return new Font(name, style, size);
	}

	// 得到随机字符串，这个方法必须在getImage()方法后调用
	//用于放在session中比较
	public String getCode() {
		return code.toString();
	}






	/**
	 * @return 随机码
	 */
	private String exctractRandCode() {
		//读取配置文件得到随机码类型
		final String randCodeType = ResourceUtil.getRandCodeType();
		//得到随机码长度
		int randCodeLength = Integer.parseInt(ResourceUtil.getRandCodeLength());
		if (randCodeType == null) {
			return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
		} else {
			switch (randCodeType.charAt(0)) {
				case '1':
					return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
				case '2':
					return RandCodeImageEnum.LOWER_CHAR.generateStr(randCodeLength);
				case '3':
					return RandCodeImageEnum.UPPER_CHAR.generateStr(randCodeLength);
				case '4':
					return RandCodeImageEnum.LETTER_CHAR.generateStr(randCodeLength);
				case '5':
					return RandCodeImageEnum.ALL_CHAR.generateStr(randCodeLength);

				default:
					return RandCodeImageEnum.NUMBER_CHAR.generateStr(randCodeLength);
			}
		}
	}

	/**
	 * 验证码辅助类
	 */
	enum RandCodeImageEnum {

		/**
		 * 混合字符串
		 */
		ALL_CHAR("0123456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), // 去除小写的l和o这个两个不容易区分的字符；
		/**
		 * 字符
		 */
		LETTER_CHAR("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"),
		/**
		 * 小写字母
		 */
		LOWER_CHAR("abcdefghijklmnopqrstuvwxyz"),
		/**
		 * 数字
		 */
		NUMBER_CHAR("0123456789"),
		/**
		 * 大写字符
		 */
		UPPER_CHAR("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

		/**
		 * 待生成的字符串
		 */
		private String charStr;

		/**
		 * @return the {@link #charStr}
		 */
		public String getCharStr() {
			return charStr;
		}

		/**
		 * @param charStr
		 */
		private RandCodeImageEnum(final String charStr) {
			this.charStr = charStr;
		}

		/**
		 * 生产随机验证码
		 *
		 * @param codeLength
		 *            验证码的长度
		 * @return 验证码
		 */
		public String generateStr(final int codeLength) {
			final StringBuffer sb = new StringBuffer();
			final Random random = new Random();
			final String sourseStr = getCharStr();

			for (int i = 0; i < codeLength; i++) {
				sb.append(sourseStr.charAt(random.nextInt(sourseStr.length())));
			}
			return sb.toString();
		}

	}

}
