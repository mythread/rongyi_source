package base.util.excel;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.util.StringUtil;

public class POIColorIndex {

	private static final Log LOG = LogFactory.getLog(POIColorIndex.class);

	public HSSFColor getPOIColor(String hexColor) {

		HSSFColor color = POIColorHolder.ColorIndex.getColor(hexColor);

		if (color == null) {
			LOG.warn("unknow color[" + hexColor + "] for poi.");
		}

		return color;
	}

	public String getColorNameByColorHex(String hexColor) {

		if (hexColor == null)
			return null;

		String name = "";

		HSSFColor color = getPOIColor(hexColor.toLowerCase());

		if (color != null) {
			name = color.getClass().getName().toUpperCase();
			name = name.substring(name.indexOf("$") + 1);
		}

		return name;
	}

	public HSSFColor getPOIColorByName(String colorName) {

		colorName = colorName.toLowerCase();

		HSSFColor color = POIColorHolder.ColorIndex
				.getColorByColorName(colorName);

		if (color == null) {
			LOG.warn("unknow poi color name[" + colorName + "].");
		}

		return color;
	}

	public static String getHtmlHexKey(String poiColorHex) {

		// MAROON
		if (HSSFColor.MAROON.hexString.equals(poiColorHex)) {
			return "7f0000";
		}

		StringBuilder htmlColorHex = new StringBuilder();

		String[] colorHexes = StringUtils.split(poiColorHex, ':');

		for (String colorHex : colorHexes) {

			if (colorHex.length() < 2) {
				htmlColorHex.append("0");
				htmlColorHex.append(colorHex);
			}
			if (colorHex.length() > 2) {
				htmlColorHex.append(colorHex.substring(0, 2));
			}

		}

		return htmlColorHex.toString().toLowerCase();

	}

	private static class POIColorHolder {

		static POIColorHolder ColorIndex = new POIColorHolder();

		private Map<String, HSSFColor> colorMap = new HashMap<String, HSSFColor>();

		private Map<String, HSSFColor> colorNameMap = new HashMap<String, HSSFColor>();

		private POIColorHolder() {

			Hashtable<String, HSSFColor> poiColorTable = HSSFColor
					.getTripletHash();

			for (Map.Entry<String, HSSFColor> poiColor : poiColorTable
					.entrySet()) {
				HSSFColor color = poiColor.getValue();
				colorMap.put(getHtmlHexKey(poiColor.getKey()), color);

				String colorName = poiColor.getValue().getClass().getName()
						.toLowerCase();
				colorName = colorName.substring(colorName.indexOf("$") + 1);
				colorNameMap.put(colorName, color);
			}
		}

		public HSSFColor getColor(String hexString) {
			return colorMap.get(hexString);
		}

		public HSSFColor getColorByColorName(String colorName) {
			return colorNameMap.get(colorName);
		}

	}

	public static Integer getIndex2(HSSFColor color) {

		Field f;
		try {
			f = color.getClass().getDeclaredField("index2");
		} catch (NoSuchFieldException e) {
			// can happen because not all colors have a second index
			return null;
		}

		Short s;
		try {
			s = (Short) f.get(color);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return Integer.valueOf(s.intValue());
	}

}
