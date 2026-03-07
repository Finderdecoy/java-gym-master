package ru.yandex.practicum.gym;

import com.sun.source.tree.Tree;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());//Проверить, что за понедельник вернулось одно занятие
        assertEquals(new TreeMap<>(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));//Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainingMonday = new TreeMap<>();
        trainingMonday.put(mondayChildTrainingSession.getTimeOfDay(), List.of(mondayChildTrainingSession));

        assertEquals(trainingMonday, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> thursdayTraining = new TreeMap<>();

        thursdayTraining.put(new TimeOfDay(13, 00), List.of(thursdayChildTrainingSession));
        thursdayTraining.put(new TimeOfDay(20, 00), List.of(thursdayAdultTrainingSession));

        assertEquals(thursdayTraining, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY));

        // Проверить, что за вторник не вернулось занятий
        assertEquals(new TreeMap<>(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие

        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 00)).size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий

        assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 00)));

    }

    @Test
    public void testingBoundariesCases() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group1 = new Group("Пилатес", Age.CHILD, 60);
        Group group2 = new Group("Йога", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach1 = new Coach("Николаёв", "Виктор", "Сергеевич");
        Coach coach2 = new Coach("Пупков", "Енакентий", "Сергеевич");
        TrainingSession first = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession second = new TrainingSession(group1, coach1, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession tree = new TrainingSession(group2, coach2, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(first);
        timetable.addNewTrainingSession(second);
        timetable.addNewTrainingSession(tree);

        //Должно вернутся за Понедельник.
        TreeMap<TimeOfDay, List<TrainingSession>> testMapTrainingSessionForMonday = new TreeMap<>();
        List<TrainingSession> testListTrainingSessionForMonday = new ArrayList<>();
        testListTrainingSessionForMonday.add(first);
        testListTrainingSessionForMonday.add(second);
        testListTrainingSessionForMonday.add(tree);
        testMapTrainingSessionForMonday.put(new TimeOfDay(13, 00), testListTrainingSessionForMonday);

        assertEquals(testMapTrainingSessionForMonday, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
        assertEquals(testListTrainingSessionForMonday, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 00)));
        assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 00)));

        //Вернется пустая мапа и пустой список занятий за день и время во Вторник.

        assertEquals(new TreeMap<>(), timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
        assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY, new TimeOfDay(13, 00)));
    }

}
