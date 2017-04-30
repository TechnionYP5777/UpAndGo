package upandgo.server.logic;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import upandgo.server.model.schedule.Schedule;
import upandgo.server.model.schedule.Timetable;
import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;

/**
 * 
 * @author kobybs
 * @since 22-12-16
 * 
 * Class that builds the study schedule given information about needed courses, constraints, sorting and displaying options.
 * 
 */

@SuppressWarnings("boxing")
public class Scheduler {

	/**
	 * gets a list of courses and a list of constraints and return a possible
	 * schedule of them which doesn't break the constraints. it works for
	 * TimeConstraints for now.
	 */
	public static List<Timetable> getTimetablesList(final List<Course> lcourse, final List<TimeConstraint> cs) {
		final List<Timetable> result = new ArrayList<>();
		final ArrayList<List<LessonGroup>> lessonsGroupArray = initMainArr(lcourse);

		final ArrayList<Integer> indexes = initIndexes(lessonsGroupArray.size()), max = initMax(lessonsGroupArray);
		for (int last = indexes.size() - 1, msb;;) {
			System.out.println(indexes);
			final List<LessonGroup> lessons = getScheduleByIndexes(lessonsGroupArray, indexes);
			final Schedule $ = new Schedule();
			if(!$.addConstraintsList(cs))
				return result;

			boolean b = false;
			int lastAdded = 0;
			for (lastAdded = 0; lastAdded < lessons.size(); ++lastAdded) {
				b = $.addLesson(lessons.get(lastAdded));
				// if you can't add that lesson than all combination including
				// him are not valid
				// therefore there is no use to check the rest of them -
				// increase bit of found lesson
				// indexes.set(xxx, indexes.get(xxx)+1);
				// if found index was maxed, than find msb that wasn't maxed and
				// max it
				// if (indexes.get(xxx) > max.get(xxx)) {
				if (!b)
					break;

			}
			if (b) {
				System.out.println("^found");
				result.add($.getTimetable()); // return $;
			}

			if (!b) {
				indexes.set(lastAdded, indexes.get(lastAdded) + 1);
				if (indexes.get(lastAdded) > max.get(lastAdded)) {
					msb = lastAdded - 1;
					// find lowest index which is not yet maxed
					for (; msb >= 0; --msb)
						if (indexes.get(msb) < max.get(msb))
							break;
					// if every index is max than we made all combinations
					if (msb < 0)
						break;
					// increase msb and zero everything to its right
					indexes.set(msb, indexes.get(msb) + 1);
					for (int xxx = msb + 1; xxx <= last; ++xxx)
						indexes.set(xxx, 0);
				}
				continue;
			}

			// increase last index by 1;
			indexes.set(last, indexes.get(last) + 1);

			if (indexes.get(last) > max.get(last)) {
				msb = last - 1;
				// find lowest index which is not yet maxed
				for (; msb >= 0; --msb)
					if (indexes.get(msb) < max.get(msb))
						break;
				// if every index is max than we made all combinations
				if (msb < 0)
					break;
				// increase msb and zero everything to its right
				indexes.set(msb, indexes.get(msb) + 1);
				for (int xxx = msb + 1; xxx <= last; ++xxx)
					indexes.set(xxx, 0);
			}
		}
		return result;
	}

	/**
	 * gets a list of courses and a list of constraints and return a possible
	 * schedule of them which doesn't break the constraints. it works for
	 * TimeConstraints for now.
	 */
	@Deprecated
	public static Schedule schedule(final List<Course> lcourse,
			@SuppressWarnings("unused") final List<TimeConstraint> __) {
		final ArrayList<List<LessonGroup>> lessonsGroupArray = initMainArr(lcourse);

		final ArrayList<Integer> indexes = initIndexes(lessonsGroupArray.size()), max = initMax(lessonsGroupArray);
		for (int last = indexes.size() - 1, msb;;) {
			System.out.println(indexes);
			final List<LessonGroup> lessons = getScheduleByIndexes(lessonsGroupArray, indexes);
			final Schedule $ = new Schedule();

			boolean b = false;
			int lastAdded = 0;
			for (lastAdded = 0; lastAdded < lessons.size(); ++lastAdded) {
				b = $.addLesson(lessons.get(lastAdded));
				// if you can't add that lesson than all combination including
				// him are not valid
				// therefore there is no use to check the rest of them -
				// increase bit of found lesson
				// indexes.set(xxx, indexes.get(xxx)+1);
				// if found index was maxed, than find msb that wasn't maxed and
				// max it
				// if (indexes.get(xxx) > max.get(xxx)) {
				if (!b)
					break;

			}
			if (b)
				return $;

			if (!b) {
				indexes.set(lastAdded, indexes.get(lastAdded) + 1);
				if (indexes.get(lastAdded) > max.get(lastAdded)) {
					msb = lastAdded - 1;
					// find lowest index which is not yet maxed
					for (; msb >= 0; --msb)
						if (indexes.get(msb) < max.get(msb))
							break;
					// if every index is max than we made all combinations
					if (msb < 0)
						break;
					// increase msb and zero everything to its right
					indexes.set(msb, indexes.get(msb) + 1);
					for (int xxx = msb + 1; xxx <= last; ++xxx)
						indexes.set(xxx, 0);
				}
				continue;
			}

			// increase last index by 1;
			indexes.set(last, indexes.get(last) + 1);

			if (indexes.get(last) > max.get(last)) {
				msb = last - 1;
				// find lowest index which is not yet maxed
				for (; msb >= 0; --msb)
					if (indexes.get(msb) < max.get(msb))
						break;
				// if every index is max than we made all combinations
				if (msb < 0)
					break;
				// increase msb and zero everything to its right
				indexes.set(msb, indexes.get(msb) + 1);
				for (int xxx = msb + 1; xxx <= last; ++xxx)
					indexes.set(xxx, 0);
			}
		}
		return null;
	}


	private static ArrayList<List<LessonGroup>> initMainArr(final List<Course> lcourse) {
		final ArrayList<List<LessonGroup>> $ = new ArrayList<>();
		for (final Course xxx : lcourse) {
			if (!xxx.getLectures().isEmpty())
				$.add(xxx.getLectures());
			if (!xxx.getTutorials().isEmpty())
				$.add(xxx.getTutorials());
		}
		return $;
	}

	private static ArrayList<Integer> initIndexes(final int size) {
		final ArrayList<Integer> $ = new ArrayList<>();
		for (int xxx = 0; xxx < size; ++xxx)
			$.add(0);
		return $;

	}

	private static ArrayList<Integer> initMax(final ArrayList<List<LessonGroup>> lessonsGroupArray) {
		final ArrayList<Integer> $ = new ArrayList<>();
		for (final List<LessonGroup> xxx : lessonsGroupArray)
			$.add(xxx.size() - 1);
		return $;

	}

	private static List<LessonGroup> getScheduleByIndexes(final ArrayList<List<LessonGroup>> lessonsGroupArray,
			final ArrayList<Integer> indexes) {
		final List<LessonGroup> $ = new ArrayList<>();
		for (int xxx = 0; xxx < indexes.size(); ++xxx)
			$.add(lessonsGroupArray.get(xxx).get(indexes.get(xxx)));
		return $;

	}

	/**
	 * 
	 * @param orig
	 * @param byDaysoff
	 * @param byBlankSpace
	 * @returns an iterator of Timetable sorted by summerized rank of chosen
	 *          paramaters
	 */
	public static Iterator<Timetable> sortedBy(final List<Timetable> orig, final boolean byDaysoff,
			final boolean byBlankSpace, final LocalTime byStartTime, final LocalTime byEndTime) {
		final List<Timetable> $ = new ArrayList<>(orig);
		Collections.sort($, (t1, t2) -> {
			Double rank1 = 0.0, rank2 = 0.0;
			if (byDaysoff) {
				rank1 += t1.getRankOfDaysoff();
				rank2 += t2.getRankOfDaysoff();
			}
			if (byBlankSpace) {
				rank1 += t1.getRankOfBlankSpace();
				rank2 += t2.getRankOfBlankSpace();
			}
			if (byStartTime != null) {
				rank1 += t1.getRankOfStartTime(byStartTime);
				rank2 += t2.getRankOfStartTime(byStartTime);
			}
			if (byEndTime != null) {
				rank1 += t1.getRankOfEndTime(byEndTime);
				rank2 += t2.getRankOfEndTime(byEndTime);
			}

			return -rank1.compareTo(rank2);
		});
		return $.iterator();
	}

	/**
	 * 
	 * @param orig
	 * @param byDaysoff
	 * @param byBlankSpace
	 * @returns an iterator of Timetable sorted by summerized rank of chosen
	 *          paramaters
	 */
	@Deprecated
	public static Iterator<Timetable> sortedBy(final List<Timetable> orig, final boolean byDaysoff,
			final boolean byBlankSpace) {
		final List<Timetable> $ = new ArrayList<>(orig);
		Collections.sort($, (t1, t2) -> {
			Double rank1 = 0.0, rank2 = 0.0;
			if (byDaysoff) {
				rank1 += t1.getRankOfDaysoff();
				rank2 += t2.getRankOfDaysoff();
			}
			if (byBlankSpace) {
				rank1 += t1.getRankOfBlankSpace();
				rank2 += t2.getRankOfBlankSpace();
			}

			return -rank1.compareTo(rank2);
		});
		return $.iterator();
	}

}
