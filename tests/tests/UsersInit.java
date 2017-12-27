package tests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.UsersDao;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.Region;
import com.mod.bean.Users;
import com.mod.mapper.RegionMapper;
import com.mod.mapper.UsersMapper;
import com.util.DbConn;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
//@Component
public class UsersInit {
	static Logger log = Logger.getLogger(UsersInit.class.getName());
	@Autowired
	private RegionMapper rm;
	@Autowired
	UsersMapper um;
	//SELECT COUNT(1),city FROM users GROUP BY city
	@Test
	public void testM2(){
		boolean b = init(300);
		System.out.println(b);
	}
	//DELETE FROM users WHERE uid NOT IN(1,2,3)
	//alter table users AUTO_INCREMENT=4;
	public boolean init(int sum) {
		int i = 0;
		Random rand = new Random();

		Users u;
		String name;
		String pinyin;
		String uuid;
		String pwd;
		String idCard;
		int error = 0;
		int subIndex = 0;
			while (i++ < sum) {
				try {
				u = new Users();
				uuid = getUUID();
				name = getChineseName();	
				pinyin = converterToFirstSpell(name)+uuid.substring(15, 19);
				subIndex = rand.nextInt(14);
				pwd = uuid.substring(subIndex, subIndex + 8);
				idCard = uuid.substring(subIndex, subIndex + 18);
				u.setUname(pinyin);
				u.setUpwd(pwd);
				u.setPhone(getTel());
				u.setCity(randCityName());
				u.setRegdate(sf.parse(randomDate()));
				u.setIdcard(idCard);
				u.setRealname(name);
				u.setAge(rand.nextInt(46) + 15);// 年龄15-60
				u.setEmail(getEmail(10, 15));
				u.setSex(name_sex);
				um.insertSelective(u);
				System.out.println(i+"--"+sum);
				} catch (Exception e) {
					error+=1;
					System.out.println(error);
					continue;
				}
			}
		return true;
	}


	public void testMethod() {
		System.out.println(converterToFirstSpell("向蓬"));

		Users[] ulist = new Users[10];
		int m = 0;
//		while (m < ulist.length) {
//
//			ulist[m] = u;
//			m++;
//		}

		int i = 0;
		Region r;
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ulist));
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mapper.setSerializationInclusion(Include.NON_NULL);
		while (i++ < 10) {
			r = randomCity();

			try {
				System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(r));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static String randomDate() {
		Calendar calendar = Calendar.getInstance();
		// 注意月份要减去1
		calendar.set(2000, 1, 1);
		calendar.getTime().getTime();
		// 根据需求，这里要将时分秒设置为0
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long min = calendar.getTime().getTime();
		;
		calendar.set(2017, 12, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.getTime().getTime();
		long max = calendar.getTime().getTime();
		// 得到大于等于min小于max的double值
		double randomDate = Math.random() * (max - min) + min;
		// 将double值舍入为整数，转化成long类型
		calendar.setTimeInMillis(Math.round(randomDate));
		return sf.format(calendar.getTime());
	}

	static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void getPwd() {
		String pwd = randomDate();
		System.out.println(pwd);
		// return pwd;
	}

	ArrayList<Region> lists = null;

	public static String getUUID() {

		// Long timeM = System.currentTimeMillis();
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr = str.replace("-", "");

		return uuidStr;
	}

	static Random r = new Random();

	public String randCityName() {
		if (lists == null) {
			lists = getCityList(2);
		}
		int max = lists.size();
		max = r.nextInt(max);
		Region temp = lists.get(max);
		return temp.getName();
	}

	public Region randomCity() {
		if (lists == null) {
			lists = getCityList(2);
		}
		Random rand = new Random();
		int max = lists.size();
		max = rand.nextInt(max);
		Region temp = lists.get(max);
		return temp;
	}

	public ArrayList<Region> getCityList(int level) {
		ArrayList<Region> list = null;
		list = rm.selectByLevel(level);
		return list;
	}

	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			String s = String.valueOf(nameChar[i]);
			if (s.matches("[\\u4e00-\\u9fa5]")) {
				try {
					String[] mPinyinArray = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
					pinyinName += mPinyinArray[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";
	private static String firstName = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福百家姓续";
	private static String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
	private static String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
	private static final String[] email_suffix = "@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn"
			.split(",");

	public static int getNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}

	/**
	 * 返回Email
	 * 
	 * @param lMin
	 *            最小长度
	 * @param lMax
	 *            最大长度
	 * @return
	 */
	public static String getEmail(int lMin, int lMax) {
		int length = getNum(lMin, lMax);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = (int) (Math.random() * base.length());
			sb.append(base.charAt(number));
		}
		sb.append(email_suffix[(int) (Math.random() * email_suffix.length)]);
		return sb.toString();
	}

	/**
	 * 返回手机号码
	 */
	private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153"
			.split(",");

	private static String getTel() {
		int index = getNum(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
		String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
		return first + second + third;
	}

	/**
	 * 返回中文姓名
	 */
	private static String name_sex = "";

	private static String getChineseName() {
		int index = getNum(0, firstName.length() - 1);
		String first = firstName.substring(index, index + 1);
		int sex = getNum(0, 1);
		String str = boy;
		int length = boy.length();
		if (sex == 0) {
			str = girl;
			length = girl.length();
			name_sex = "女";
		} else {
			name_sex = "男";
		}
		index = getNum(0, length - 1);
		String second = str.substring(index, index + 1);
		int hasThird = getNum(0, 1);
		String third = "";
		if (hasThird == 1) {
			index = getNum(0, length - 1);
			third = str.substring(index, index + 1);
		}
		return first + second + third;
	}

}
