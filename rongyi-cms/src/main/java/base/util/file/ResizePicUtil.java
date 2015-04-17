package base.util.file;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class ResizePicUtil {

	/**
	 * 生成图片缩略
	 * 
	 * @param source
	 * @param targetW
	 * @param targetH
	 * @param ifScaling 是否等比缩放
	 * @return
	 */
	public static BufferedImage resize(BufferedImage source, int targetW, int targetH, boolean ifScaling) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if(ifScaling) {
			if (sx > sy) {
				sx = sy;
				targetW = (int) (sx * source.getWidth());
			} else {
				sy = sx;
				targetH = (int) (sy * source.getHeight());
			}
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	public static void saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int hight, boolean isCut) throws Exception {
		try {
			BufferedImage srcImage;
			// String ex =
			// fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
			String imgType = "JPEG";
			if (fromFileStr.toLowerCase().endsWith(".png")) {
				imgType = "PNG";
			}
			// System.out.println(ex);
			File saveFile = new File(saveToFileStr);
			File fromFile = new File(fromFileStr);
			// 创建文件夹
			if (!saveFile.exists()) {
				saveFile.mkdirs();
			}
			if (!saveFile.exists()) {
				try {
					saveFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			srcImage = ImageIO.read(fromFile);
			if(isCut) {
				if (width > 0 || hight > 0) {
					srcImage = resize(srcImage, width, hight, false);
				}
			}
			ImageIO.write(srcImage, imgType, saveFile);
		}catch(IIOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String argv[]) {
		try {
			// 参数1(from),参数2(to),参数3(宽),参数4(高)
//			ResizePicUtil.saveImageAsJpg("E:/Document/My Pictures/3.gif", "c:/6.gif", 50, 50);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
