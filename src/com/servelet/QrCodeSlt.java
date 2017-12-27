package com.servelet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mod.bean.Qrcheck;
import com.mod.bean.Users;
import com.service.QrcheckService;
import com.service.SpCarService;
import com.service.UserService;

/**
 * Servlet implementation class QrCodeSlt
 */
@WebServlet("/QrCode")
@Controller
public class QrCodeSlt extends HttpServlet {
	static int WIDTH = 400;
	static int HEIGHT = 400;
	File logoFile;
	@Autowired
	UserService userService;
	@Autowired
	QrcheckService qrcheckService;
	@Autowired
	SpCarService spCarService;

	@Override
	public void init(ServletConfig config) throws ServletException {
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,  
	              config.getServletContext());  
	    String path = config.getServletContext().getRealPath("./img/QRlogo.png");
		logoFile = new File(path);
	}
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QrCodeSlt() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String flag = request.getParameter("flag");
		if (flag == null || flag.equals("")) {
			creatQRCodeWithLogo(request, response);
			return;
			}
		PrintWriter out = response.getWriter();
		if (flag.equals("check")) {
			String uuid = (String) request.getSession().getAttribute("uuid");
			
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");
			Users users = new Users(name, pwd);
			users = userService.findUser(users);

			if (users == null) {
				out.write("<script type='text/javascript'>alert('登录失败，账户不存在！');</script>");
				
			} else {
				Qrcheck qr = new Qrcheck(uuid, 1, name, pwd);
				qrcheckService.update(qr);
				out.write("<script type='text/javascript'>alert('登录成功');</script>");
			}
		}else if (flag.equals("scan")){
			//获得二维码链接里的UUID
			String uuid = request.getParameter("UUID");
			
			qrcheckService.changeStatus(uuid,2);
			request.getSession().setAttribute("uuid", uuid);
			response.sendRedirect("qrcheck.jsp");
		}else if (flag.equals("verify")){
			String uuid = request.getParameter("uuid");
			Qrcheck qr = qrcheckService.getQrcheck(uuid);
			if(qr==null){
				out.close();
				return;
			}
			
			if (qr.getStatus() == 0) {
				out.write("{\"message\":\"验证失败！\"}");
			} else if (qr.getStatus() == 2) {
				out.write("{\"message\":\"已扫描\"}");
				
			}else if (qr.getStatus() == 1) {
				Users users = new Users(qr.getName(), qr.getPwd());
				users = userService.findUser(users);
				if (users == null) {
					out.write("{\"message\":\"验证失败！\"}");
				} else {
					Integer uid = users.getUid();
					request.getSession().setAttribute("name", qr.getName());
					request.getSession().setAttribute("uid", uid);
					request.getSession().setAttribute("avatar", users.getAvatar());
					request.getSession().setAttribute("city", users.getCity());
					int num = spCarService.getCarNum(uid);
					request.getSession().setAttribute("carnum", num);

					ObjectMapper mapper = new ObjectMapper();
					// 借密码变量 存储 购物车数量
					users.setUpwd(num + "");
					String json = mapper.writeValueAsString(users);
					
					out.write(json);
					out.flush();
					qrcheckService.deleQrcheck(uuid);
				}

			}

		}
		if (out != null) {
			out.close();
		}

	}

	static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public static String getUUID() {
		String time = sf.format(new Date());
		// Long timeM = System.currentTimeMillis();
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr = str.replace("-", "");
		return time + "-" + uuidStr;
	}



	public void creatQRCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream ou = response.getOutputStream();
		String st = null;
		//String uuid = getUUID();
		String uuid = request.getParameter("UUID");
		//String Addr = request.getLocalAddr();
		String Addr = request.getServerName();//"60.205.225.65";
		int port = request.getLocalPort();
		
		st = "http://" + Addr + ":" + port + request.getContextPath()+"/QrCode?flag=scan&&UUID=" + uuid;
		Qrcheck q = new Qrcheck(uuid, 0);
		boolean b = qrcheckService.insertQrcheck(q);
		if (!b) {
			// 二维码插入数据库失败返回
			return;
		}
		String format = "png";// 图像类型
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 控制边宽
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(st, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
		} catch (WriterException e) {
			e.printStackTrace();
			
		}
		// 生成矩阵
		MatrixToImageWriter.writeToStream(bitMatrix, format, ou);
		ou.flush();
		ou.close();
	}
	public void creatQRCodeWithLogo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStream ou = response.getOutputStream();
		String st = null;
		String uuid = request.getParameter("UUID");
		//String Addr = request.getLocalAddr();
		String Addr = request.getServerName();//"60.205.225.65";
		int port = request.getLocalPort();
		st = "http://" + Addr + ":" + port + request.getContextPath()+"/QrCode?flag=scan&&UUID=" + uuid;
		Qrcheck q = new Qrcheck(uuid, 0);
		boolean b = qrcheckService.insertQrcheck(q);
		if (!b) {
			// 二维码插入数据库失败返回
			return;
		}
		
		BufferedImage img = createQRCode(logoFile, st, "欢迎登录!");
		ImageIO.write(img, "png", ou);
		ou.flush();
		ou.close();
	}
	
	public  BufferedImage createQRCode(File logoFile, String content, String text) {
		try {
			// 生成二维码bufferedImage图片
			MultiFormatWriter multiFormatWriter = null;
			BitMatrix bm = null;
			BufferedImage image = null;
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			// 设置编码方式
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.MARGIN, 1);
			try {
				multiFormatWriter = new MultiFormatWriter();
				// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
				bm = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
				image = MatrixToImageWriter.toBufferedImage(bm);
			} catch (WriterException e) {
				e.printStackTrace();
			}
			BufferedImage bim = image;
			
			// 给二维码图片添加Logo并保存到指定位置，返回base64编码的图片数据字符串
			return createLogoQRCode(bim, logoFile, text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public  BufferedImage createLogoQRCode( BufferedImage bim, File logoPic, String text) {
		try {
			/**
			 * 读取二维码图片，并构建绘图对象
			 */
			BufferedImage image = bim;

			// 如果logo图片存在，则加入到二维码图片中
			if (logoPic != null && logoPic.exists()) {
				//新建BufferedImage先画二维码再画logo最后替换原始image；
				
				BufferedImage result = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = result.createGraphics();
				
				g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
				/**
				 * 读取Logo图片
				 */
				BufferedImage logo = ImageIO.read(logoPic);

				/**
				 * 设置logo的大小,本人设置为二维码图片的23%
				 */
				int tempw = (int) (WIDTH * 2.3 / 10);
				int temph = (int) (HEIGHT * 2.3 / 10);
				int widthLogo = WIDTH > tempw ? tempw : WIDTH, heightLogo = HEIGHT > temph ? temph : HEIGHT;

				/**
				 * logo放在中心
				 */
				int x = (WIDTH - widthLogo) / 2;
				int y = (HEIGHT - heightLogo) / 2;
			

				// 开始绘制图片

				g.drawImage(logo, x, y, widthLogo, heightLogo, null);
				g.setColor(Color.BLUE);
				g.setStroke(new BasicStroke(2));
				g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);

				g.dispose();
				// logo.flush();
				result.flush();
				image = result;
			}

			// 把商品名称添加上去，商品名称不要太长，这里最多支持两行。太长就会自动截取
			if (text != null && !text.equals("")) {
				// 新的图片，把带logo的二维码下面加上文字
				BufferedImage outImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
				Graphics2D outg = outImage.createGraphics();
				// 画二维码到新的面板
				outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
				// 画文字到新的面板
				outg.setColor(Color.BLUE);
				outg.setFont(new Font("华文行楷", Font.BOLD, 22)); // 字体、字型、字号
				int strWidth = outg.getFontMetrics().stringWidth(text);

				outg.drawString(text, (WIDTH - strWidth) / 2, HEIGHT - 7); // 画文字

				outg.dispose();
				outImage.flush();
				image = outImage;
			}
			// logo.flush();
			image.flush();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.flush();
			ImageIO.write(image, "png", baos);

			// 如果输出路径为空，则不保存二维码图片到指定路径下
			//if (!"".equals(outPath.trim())) {
				// 二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
				// 可以看到这个方法最终返回的是这个二维码的imageBase64字符串
				// 前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>
				// 其中${imageBase64QRCode}对应二维码的imageBase64字符串
			//	ImageIO.write(image, "png", new File(outPath + "\\" + new Date().getTime() + ".png"));
			//}

			// 获取base64编码的二维码图片字符串
			//String imageBase64QRCode = Base64.encodeBase64String(baos.toByteArray());
			baos.close();
			//return imageBase64QRCode;
			return image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
