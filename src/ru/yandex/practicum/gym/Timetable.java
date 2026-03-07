package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        timetable.put(trainingSession.getDayOfWeek(), addTrainingInList(trainingSession));
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> addTrainingInList(TrainingSession trainingSession) {
        TreeMap<TimeOfDay, List<TrainingSession>> training = timetable.get(trainingSession.getDayOfWeek());
        List<TrainingSession> trainingOfTime;

        if (training == null) training = new TreeMap<>(TimeOfDay::compareTo);
        trainingOfTime = training.get(trainingSession.getTimeOfDay());

        if (trainingOfTime == null) trainingOfTime = new ArrayList<>();
        trainingOfTime.add(trainingSession);

        training.put(trainingSession.getTimeOfDay(), trainingOfTime);
        return training;
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> timetableOfDay = new TreeMap<>();
        if (timetable.get(dayOfWeek) != null) {
            timetableOfDay = timetable.get(dayOfWeek);
        }
        return timetableOfDay;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        List<TrainingSession> trainingOnTime = new ArrayList<>();

        if (getTrainingSessionsForDay(dayOfWeek).get(timeOfDay) != null) {
            trainingOnTime = getTrainingSessionsForDay(dayOfWeek).get(timeOfDay);
        }
        return trainingOnTime;
    }

    public List<CountOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> countOfTraning = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> value : timetable.values()) {
            for (List<TrainingSession> trainingSessions : value.values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    Coach coach = trainingSession.getCoach();
                    countOfTraning.put(coach, countOfTraning.getOrDefault(coach, 0) + 1);
                }
            }
        }
        List<CountOfTrainings> countOfTrainingsList = new ArrayList<>();

        for (Coach coach : countOfTraning.keySet()) {
            countOfTrainingsList.add(new CountOfTrainings(coach, countOfTraning.get(coach)));
        }

        countOfTrainingsList.sort(new CountOfTrainings());


        return countOfTrainingsList.reversed();
    }
}
