package base.util.collection;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class CollectionUtil {

	public static boolean isEmpty(Collection<?> c) {
		return null == c || c.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> c) {
		return !isEmpty(c);
	}

	public static <T> List<T> addFirst(T obj, List<T> list) {
		list.add(0, obj);
		return list;
	}

	/**
	 * remove last from index
	 * 
	 * @param index
	 * @return List
	 */
	public static <T> List<T> removeLast(List<T> list, int index) {
		while (list.size() > index) {
			list.remove(list.size() - 1);
		}

		return list;
	}

	public static <T> T poll(List<T> list, int index) {
		if (list.size() - 1 < index) {
			return null;
		}

		T t = list.get(index);
		list.remove(index);

		return t;
	}

	/**
	 * make the elements of list be unique
	 * 
	 * @param <T>
	 * @param source
	 */
	public static <T> void distinct(List<T> source) {

		if (source == null)
			return;

		LinkedHashSet<T> uniSet = new LinkedHashSet<T>();
		uniSet.addAll(source);

		source.clear();
		source.addAll(uniSet);
	}

	/**
	 * convert the string elements to numbers
	 * 
	 * @param source
	 * @param clazz
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> toNumericType(List<String> source,
			final Class<T> clazz) {

		if (source == null || source.size() == 0)
			return new ArrayList<T>();

		List<T> covertedList;

		if (source instanceof ArrayList<?>) {
			covertedList = new ArrayList<T>(source.size());
		} else if (source instanceof java.util.LinkedList<?>) {
			covertedList = new java.util.LinkedList<T>();
		} else {
			throw new IllegalStateException("the type of source array["
					+ source.getClass()
					+ "] can't be recognized by the method.");
		}

		for (String value : source) {

			Object result;

			if (Integer.class == clazz) {
				result = Integer.parseInt(value);
			} else {
				throw new UnsupportedOperationException(
						"toNumericType is unsupported for [" + clazz + "]");
			}

			covertedList.add((T) result);
		}

		return covertedList;

	}

	public static <T> Collection<T> removeNull(Collection<T> c) {
		if (isEmpty(c)) {
			return new ArrayList<T>(0);
		}

		Iterator<T> iter = c.iterator();

		while (iter.hasNext()) {
			if (iter.next() == null) {
				iter.remove();
			}
		}

		return c;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> removeNull(List<T> list) {
		return (List<T>) removeNull((Collection) list);
	}

	public static boolean containsIgnoreCase(String[] list, String obj) {

		if (ArrayUtils.isEmpty(list)) {
			return false;
		} else if (null == obj) {
			return false;
		} else {
			for (String s : list) {
				if (s.equalsIgnoreCase(obj)) {
					return true;
				}
			}

			return false;
		}

	}

	/**
	 * convert List to string, split by specified char
	 * 
	 * @param collection
	 * @param splitor
	 * @return String
	 */
	public static String join(Collection<String> collection, String splitor) {

		if (collection == null)
			return "";

		StringBuilder str = new StringBuilder();

		java.util.Iterator<String> it = collection.iterator();

		if (it.hasNext())
			str.append(it.next());

		while (it.hasNext()) {
			str.append(splitor);
			str.append(it.next());
		}

		return str.toString();
	}

	/**
	 * remove empty string from list.be caution the return list is ArrayList
	 * 
	 * @param list
	 * @return List<String>
	 */
	public static List<String> removeEmptyStringFromList(List<String> list) {

		List<String> noEmptyStrList = new ArrayList<String>();

		if (list != null) {
			for (String str : list) {
				if (!StringUtils.isBlank(str)) {
					noEmptyStrList.add(str);
				}
			}
		}

		return noEmptyStrList;
	}
}
