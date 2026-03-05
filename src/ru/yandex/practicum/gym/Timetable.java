package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;
    private TreeMap<TimeOfDay, List<TrainingSession>> training;
    private List<TrainingSession> trainingOfTime;

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (timetable == null) timetable = new HashMap<>();

        addTrainingInList(trainingSession);

        timetable.put(trainingSession.getDayOfWeek(), training);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> addTrainingInList(TrainingSession trainingSession) {
        training = timetable.get(trainingSession.getDayOfWeek());

        if (training == null) training = new TreeMap<>(TimeOfDay::compareTo);
        trainingOfTime = training.get(trainingSession.getTimeOfDay());

        if (trainingOfTime == null) trainingOfTime = new ArrayList<>();
        trainingOfTime.add(trainingSession);

        training.put(trainingSession.getTimeOfDay(), trainingOfTime);
        return training;
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return getTrainingSessionsForDay(dayOfWeek).get(timeOfDay);
    }

    public List<CountOfTrainings> getCountByCoaches(Timetable timetable) {
        HashMap<Coach, Integer> countOfTraning = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> value : timetable.timetable.values()) {
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

        return countOfTrainingsList;
    }
}
