package ch.bbw.zork;

import java.lang.reflect.Array;
import java.util.HashSet;

public class Helper {
	public static <T> T[] getShuffledList(HashSet<T> orderedList) {
		HashSet<T> unusedList =  new HashSet<>(orderedList);
		HashSet<T> list =  new HashSet<>(orderedList);
		T ele = list.iterator().next();
		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(ele.getClass(), orderedList.size());
		int done = 0;

		while (!unusedList.isEmpty())  {
			list.clear();
			list.addAll(unusedList);
			int randomInt = 0;
			if (!list.isEmpty()) {
				randomInt = Game.getRandom().nextInt(list.size());
			}
			int i = 0;

			for (T element : list) {
				if (randomInt == i) {
					result[done] = element;
					done++;
					unusedList.remove(element);
					break;
				}
				i++;
			}
		}

		return (T[])result;
	}

	public static String padLeft(String text, char ch, int width) {
		if (text.length() >= width) {
			return text;
		}
		StringBuilder sb = new StringBuilder();
		while (sb.length() < width - text.length()) {
			sb.append(ch);
		}
		sb.append(text);

		return sb.toString();
	}
}
