package upandgo.shared.model.scedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.rpc.IsSerializable;

import upandgo.shared.entities.LessonGroup;
import upandgo.shared.entities.LocalTime;
import upandgo.shared.entities.constraint.TimeConstraint;
import upandgo.shared.entities.course.Course;
import upandgo.shared.model.scedule.Schedule;
import upandgo.shared.model.scedule.Timetable;

/**
 * 
 * @author kobybs
 * @since 22-12-16
 * 
 * Class that builds the study schedule given information about needed courses, constraints, sorting and displaying options.
 * 
 */

@SuppressWarnings("boxing")
public class Scheduler implements IsSerializable{
	private static Map<String, Color> colorMap;
	/**
	 * gets a list of courses and a list of constraints and return a possible
	 * schedule of them which doesn't break the constraints. it works for
	 * TimeConstraints for now.
	 */
	
	public static Map<String, Color> getColorMap(){
		return colorMap;
	}
	
	public static List<Timetable> getTimetablesList(final List<Course> lcourse, final List<TimeConstraint> cs) {
		//Map<Course, Color> colorsMap = new HashMap();
		//Map<Course, Color> colorsMap = mapCoursesToColors(lcourse);
		colorMap = mapCoursesToColors(lcourse);
				
		//Log.info("Scheduler: in getTimetablesList with " + lcourse.size() + " courses");
		final List<Timetable> result = new ArrayList<>();
		//Log.info("Scheduler: here");

		final ArrayList<List<LessonGroup>> lessonsGroupArray = initMainArr(lcourse);
		//Log.info("Scheduler: here");

		final ArrayList<Integer> indexes = initIndexes(lessonsGroupArray.size()), max = initMax(lessonsGroupArray);
		//Log.info("Scheduler: here");

		for (int last = indexes.size() - 1, msb;;) {
			
			//Log.info("Scheduler: in for with indexes " + indexes);

			final List<LessonGroup> lessons = getScheduleByIndexes(lessonsGroupArray, indexes);
			final Schedule $ = new Schedule();
			if(!$.addConstraintsList(cs))
				return result;

			boolean b = false;
			int lastAdded = 0;
			for (lastAdded = 0; lastAdded < lessons.size(); ++lastAdded) {
				b = $.addLesson(lessons.get(lastAdded));
				//Log.info("************current list: " + $ + "************");
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
				//Log.info("Found");
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
		Log.info("Found " + result.size() + " correct results");
		return result;
	}
	
	public static Map<String, Color> mapCoursesToColors(final List<Course> lcourse){
		Map<String, Color> colorsMap = new HashMap();
		for(int c = 0; c < lcourse.size(); c++){
			colorsMap.put(lcourse.get(c).getId(), Color.valueOf(c));
		}
		
		return colorsMap;
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
		//Log.info("Scheduler: in initMainArr with "+ lcourse.size() + " courses");
		final ArrayList<List<LessonGroup>> $ = new ArrayList<>();
		for (final Course xxx : lcourse) {
			//Log.info("Scheduler: in initMainArr course " + xxx.getId());
			//Log.info("Scheduler: in initMainArr testing if course.getLectures(): " + xxx.getLectures());
			//Log.info("Scheduler: in initMainArr testing if course.getLectures().isEmpty(): " + xxx.getLectures().size());
			if (!xxx.getLectures().isEmpty())
				$.add(xxx.getLectures());
			//Log.info("Scheduler: in initMainArr testing if course.getTutorials().isEmpty(): " + xxx.getTutorials().size());
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
	
	public static List<Timetable> ListSortedBy(final List<Timetable> orig, final boolean byDaysoff,
			final boolean byBlankSpace, final LocalTime byStartTime, final LocalTime byEndTime, final List<Boolean> vectorDaysOff) {
		final List<Timetable> $ = new ArrayList<>(orig);
		Collections.sort($, new Comparator<Timetable>() {
			@Override
			public int compare(Timetable t1, Timetable t2) {
				Double rank1 = 0.0, rank2 = 0.0;
				if (byDaysoff) {
					//probably irrevelant now when we use seperate days.
					//rank1 += t1.getRankOfDaysoff();
					//rank2 += t2.getRankOfDaysoff();
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
				
				if (vectorDaysOff.get(0)) {
					rank1 += t1.getRankOfFreeSunday();
					rank2 += t2.getRankOfFreeSunday();
				}
				if (vectorDaysOff.get(1)) {
					rank1 += t1.getRankOfFreeMonday();
					rank2 += t2.getRankOfFreeMonday();
				}
				if (vectorDaysOff.get(2)) {
					rank1 += t1.getRankOfFreeTuesday();
					rank2 += t2.getRankOfFreeTuesday();
				}
				if (vectorDaysOff.get(3)) {
					rank1 += t1.getRankOfFreeWednesday();
					rank2 += t2.getRankOfFreeWednesday();
				}
				if (vectorDaysOff.get(4)) {
					rank1 += t1.getRankOfFreeThursday();
					rank2 += t2.getRankOfFreeThursday();
				}
				return -rank1.compareTo(rank2);
			}
		});
		return $;
	}

	public static Iterator<Timetable> sortedBy(final List<Timetable> orig, final boolean byDaysoff,
			final boolean byBlankSpace, final LocalTime byStartTime, final LocalTime byEndTime) {
		final List<Timetable> $ = new ArrayList<>(orig);
		Collections.sort($, new Comparator<Timetable>() {
			@Override
			public int compare(Timetable t1, Timetable t2) {
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
			}
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
		Collections.sort($, new Comparator<Timetable>() {
			@Override
			public int compare(Timetable t1, Timetable t2) {
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
			}
		});
		return $.iterator();
	}

}
