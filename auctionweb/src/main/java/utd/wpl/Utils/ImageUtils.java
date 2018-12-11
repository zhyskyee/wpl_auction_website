package utd.wpl.Utils;

/***********************************************
* @author hxz174130@utdallas.edu
* 
* @date Dec 10, 2018 8:57:36 AM
* 
***********************************************/
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @Description <图片相关工具类>
 * @author liwang
 * @date 2017年11月14日
 * @version
 */
public class ImageUtils {
	public static boolean baseStrToImg(byte[] base, String imgFilePath) {
		if (base == null || base.length == 0) {// 图像数据为空
			return false;
		}
		try {
			// Base64解码
//			byte[] bytes = Base64.getDecoder().decode(base);
//			for (int i = 0; i < bytes.length; ++i) {
//				if (bytes[i] < 0) {// 调整异常数据
//					bytes[i] += 256;
//				}
//			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			System.out.println("========outputAImgTO>>>"+imgFilePath);
			out.write(base);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static byte[] imgToBaseStr(InputStream in) {
		byte[] data = null;
		// 读取图片字节数组
		try {
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		return data; // 返回Base64编码过的字节数组字符串
	}
}
