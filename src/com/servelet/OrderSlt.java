package com.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.Address;
import com.mod.bean.Goods;
import com.mod.bean.OrderForm;
import com.mod.bean.Orders;
import com.service.GoodsService;
import com.service.OrdersService;
import com.service.SpCarService;
import com.service.UserService;

/**
 * Servlet implementation class OrderSlt
 */
@WebServlet("/Order")
@Component
public class OrderSlt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	UserService userService;
	@Autowired
	OrdersService ordersService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	SpCarService spCarService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderSlt() {
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
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		this.doPost(request, response);
	}

	static Logger log = Logger.getLogger(OrderSlt.class.getName());

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String flag = request.getParameter("flag");
		if (flag == null) {
			// System.out.println("空FALG");
			log.warn("空Flag!");
			return;
		}
		PrintWriter out = response.getWriter();
		Object temp = request.getSession().getAttribute("uid");
		if (temp == null) {
			out.write("{\"message\":\"用户未登录,添加失败！\"}");
			out.flush();
			out.close();
			return;
		}
		int uid = (int) temp;
		int num = spCarService.getCarNum(uid);
		request.getSession().setAttribute("carnum", num);
		if (flag.equalsIgnoreCase("payOrder")) {// 支付已存在订单
			String oid = (String) request.getParameter("oid");
			LinkedList<HashMap<String, Object>> orderslist = ordersService.getOrderList(oid, uid);
			HashMap<String, Object> hm = ordersService.jsonFactory(orderslist);
			ObjectMapper mapper = new ObjectMapper();
			String hmJson = mapper.writeValueAsString(hm);
			request.getSession().setAttribute("hm", hmJson);
			// request.getRequestDispatcher("jsp/order.jsp").forward(request,
			// response);
			response.sendRedirect("jsp/order.jsp");
			out.close();
			return;
		} else if (flag.equalsIgnoreCase("multi")) {
			// 下单购买一堆
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			String oid = sf.format(new Date());
			if (addOrders(request, response, oid)) {
				// 获取当前订单视图
				LinkedList<HashMap<String, Object>> orderslist = ordersService.getOrderList(oid, uid);
				HashMap<String, Object> hm = ordersService.jsonFactory(orderslist);
				ObjectMapper mapper = new ObjectMapper();
				String hmJson = mapper.writeValueAsString(hm);
				request.getSession().setAttribute("hm", hmJson);
				// request.getRequestDispatcher("jsp/order.jsp").forward(request,
				// response);
				response.sendRedirect("jsp/order.jsp");
				out.close();
				return;
			} else {
				out.write("添加失败！");
			}
			out.flush();
		} else if (flag.equalsIgnoreCase("single")) {

			// 商品界面直接购买
			creatFakeView(request);

			out.write("order.jsp");
			out.flush();
			// response.sendRedirect("jsp/order.jsp");
		} else if (flag.endsWith("pay")) {
			String payPwd = request.getParameter("payPwd");
			String oid = request.getParameter("oid");
			if (payPwd.equalsIgnoreCase("123456")) {
				out.write("{\"message\":\"支付成功！\",\"num\":" + 0 + "}");
			} else {
				out.write("{\"message\":\"支付失败！\"}");
				out.flush();
				out.close();
				return;
			}
			if (flag.equalsIgnoreCase("mpay")) {
				log.info(oid + "--订单付款！");
				ordersService.updateByOUid(oid, uid);
			} else if (flag.equalsIgnoreCase("spay")) {
				String address = request.getParameter("addr");
				String ofJson = (String) request.getSession().getAttribute("ofJson");
				String oJson = (String) request.getSession().getAttribute("oJson");
				// System.out.println(ofJson);
				// System.out.println(oJson);
				ObjectMapper mapper = new ObjectMapper();
				Orders orders = mapper.readValue(oJson, Orders.class);
				OrderForm orderform = mapper.readValue(ofJson, OrderForm.class);
				orders.setState("已付款");
				orders.setAddress(address);
				ordersService.addOrders(orders, orderform);
			}
		} else if (flag.equals("allorder")) {

			ObjectMapper mapper = new ObjectMapper();
			LinkedList<LinkedHashMap<String, Object>> ogview = ordersService.getOGViewGoupByOid(uid);
			String json = mapper.writeValueAsString(ogview);
			request.getSession().setAttribute("ogJson", json);
			response.sendRedirect("jsp/allorders.jsp");
		}
		out.close();
	}

	public void creatFakeView(HttpServletRequest request) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		HashMap<String, Object> goodsMap = new HashMap<String, Object>();
		SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
		String oid = smf.format(new Date());
		// uid
		int gid = Integer.parseInt(request.getParameter("gid"));
		Goods goods = goodsService.getGoods(gid);
		String gname = goods.getGname();
		int gnum = Integer.parseInt(request.getParameter("gnum"));
		double price = goods.getPrice();
		double SubTotal = price * gnum;
		double sum = SubTotal;
		String imgPath = goods.getImgpath();
		int stock = goods.getStock();
		int tid = goods.getTid();
		String address = null;
		String descption = request.getParameter("desc");
		ObjectMapper mapper = new ObjectMapper();
		String hmJson = null;
		int uid = (int) request.getSession().getAttribute("uid");
		hm.put("oid", oid);
		hm.put("uid", uid);
		goodsMap.put("gid", gid);
		goodsMap.put("gname", gname);
		goodsMap.put("gnum", gnum);
		goodsMap.put("price", price);
		goodsMap.put("SubTotal", SubTotal);
		hm.put("sum", sum);
		goodsMap.put("tid", tid);
		goodsMap.put("descption", descption);
		goodsMap.put("imgPath", imgPath);
		goodsMap.put("stock", stock);
		hm.put("address", "#");
		hm.put("goods", goodsMap);
		Orders o = new Orders(oid, uid, sum, address);
		OrderForm of = new OrderForm();
		of.setDescption(descption);
		of.setGid(gid);
		of.setGnum(gnum);
		of.setOid(oid);
		of.setSubtotal(SubTotal);
		of.setUid(uid);
		String ofJson = null;
		String oJson = null;

		try {
			hmJson = mapper.writeValueAsString(hm);
			ofJson = mapper.writeValueAsString(of);
			oJson = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("hm", hmJson);
		request.getSession().setAttribute("ofJson", ofJson);
		request.getSession().setAttribute("oJson", oJson);
		LinkedList<Address> adresList = userService.getAdress(uid);
		String adresJson = null;
		try {
			adresJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(adresList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("adresJson", adresJson);
	}

	private Boolean addOrders(HttpServletRequest request, HttpServletResponse response, String oid) {
		LinkedList<OrderForm> orderlist = null;
		Orders orders = null;
		LinkedList<Integer> buyCids = null;
		try {
			int uid = (int) request.getSession().getAttribute("uid");
			buyCids = new LinkedList<Integer>();
			String[] indexs = request.getParameter("buyCids").split(",");
			for (String temp : indexs) {
				buyCids.add(Integer.parseInt(temp));
			}

			String address = request.getParameter("adresId");
			LinkedList<Integer> tempcids = new LinkedList<Integer>();
			tempcids.addAll(buyCids);

			// orderfor表添加
			// LinkedList<ShoppingCar> list = spCarService.getCarListByUid(uid);
			LinkedList<HashMap<String, Object>> view = spCarService.getCarView(uid);
			orderlist = new LinkedList<OrderForm>();

			OrderForm tempOrder = null;
			double sum = 0;
			for (HashMap<String, Object> temp : view) {
				// int cid = (int)temp.get("cid");
				if (tempcids.contains(temp.get("cid"))) {
					tempcids.remove(temp.get("cid"));
				} else {
					continue;
				}
				tempOrder = new OrderForm();
				tempOrder.setOid(oid);
				tempOrder.setUid(uid);
				tempOrder.setGid((Integer) temp.get("gid"));
				int gnum = (int) temp.get("gnum");
				double price = (double) temp.get("price");
				tempOrder.setGnum(gnum);
				double subtotal = gnum * price;
				tempOrder.setSubtotal(subtotal);
				tempOrder.setDescption((String) temp.get("descption"));
				orderlist.add(tempOrder);
				sum += subtotal;
			}
			orders = new Orders(oid, uid, sum, address);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		if (orders == null || orderlist == null || buyCids == null) {
			return false;
		}
		return ordersService.addOrders(orders, orderlist, buyCids);
	}

}
