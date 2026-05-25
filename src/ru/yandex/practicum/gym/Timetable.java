package ru.yandex.practicum.gym;


import java.util.*;
import java.util.Map.Entry;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    //Сразу же подсчитывать количество тренировок
    private HashMap<Coach, Integer> coachesCounter = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        if (trainingSession == null || trainingSession.getDayOfWeek() == null || trainingSession.getTimeOfDay() == null) {
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

        Coach currentCoach = trainingSession.getCoach();
        coachesCounter.put(currentCoach, coachesCounter.getOrDefault(currentCoach, 0) + 1);

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
//
        List<CounterOfTrainings> counterOfTrainingsList = new ArrayList<>();
        for (Coach coach : coachesCounter.keySet()) {
            CounterOfTrainings counterOfTrainings = new CounterOfTrainings(coach, coachesCounter.get(coach));
            counterOfTrainingsList.add(counterOfTrainings);
        }
        List<Map.Entry<Coach, Integer>> coaches = new ArrayList<>(coachesCounter.entrySet());
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
