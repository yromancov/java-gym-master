package ru.yandex.practicum.gym;


import java.util.*;
import java.util.Map.Entry;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        if (trainingSession == null || trainingSession.getDayOfWeek() == null || trainingSession.getTimeOfDay() == null) {

            System.out.println("Добавить запись не удалось");
            return;
        }
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<>());
        }

        TreeMap<TimeOfDay, List<TrainingSession>> todayGym = timetable.get(day);
        if (!todayGym.containsKey(time)) {
            todayGym.put(time, new ArrayList<>());
        }
        List<TrainingSession> sessions = todayGym.get(time);
        sessions.add(trainingSession);

        System.out.println("Занятие успешно добавлено!");
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek);
        }
        return new TreeMap<>();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (!timetable.containsKey(dayOfWeek)) {
            return new ArrayList<>();
        }
        TreeMap<TimeOfDay, List<TrainingSession>> todayGym = timetable.get(dayOfWeek);
        if (!todayGym.containsKey(timeOfDay)) {
            return new ArrayList<>();
        }
        return todayGym.get(timeOfDay);
    }

    public LinkedHashMap<Coach, Integer> getCountByCoaches() {
        Map<Coach, Integer> coachCount = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> todayGym : timetable.values()) {
            for (List<TrainingSession> sessions : todayGym.values()) {
                for (TrainingSession trainingSession : sessions) {
                    Coach coach = trainingSession.getCoach();
                    if (coachCount.containsKey(coach)) {
                        coachCount.put(coach, coachCount.get(coach) + 1);
                    } else coachCount.put(coach, 1);
                }
            }
        }
        List<Map.Entry<Coach, Integer>> coaches = new ArrayList<>(coachCount.entrySet());
        Collections.sort(coaches,
                new Comparator<Map.Entry<Coach, Integer>>() {

                    @Override
                    public int compare(Map.Entry<Coach, Integer> c1,
                                       Map.Entry<Coach, Integer> c2) {

                        return c2.getValue() - c1.getValue();
                    }
                });
        LinkedHashMap<Coach, Integer> result =
                new LinkedHashMap<>();

        for (Map.Entry<Coach, Integer> entry : coaches) {

            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
