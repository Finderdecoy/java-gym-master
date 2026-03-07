package ru.yandex.practicum.gym;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestCoachCountTraining {
    private static Timetable timetable;
    private static Group group;
    private static Coach coach;
    private static Coach coach1;
    private static Coach coach2;
    private static Coach coach3;
    private static Coach coach4;
    private static List<CountOfTrainings> gettedList;

    @BeforeAll
    public static void beforeAll() {
        timetable = new Timetable();

        group = new Group("Акробатика для детей", Age.CHILD, 60);

        coach = new Coach("Васильев", "Николай", "Сергеевич");
        coach2 = new Coach("Дмитрий", "Жуков", "Васильевич");
        coach1 = new Coach("Константин", "Петров", "Михайлович");
        coach3 = new Coach("Некита", "Борисюк", "Сергеевич");
        coach4 = new Coach("Стас", "Безрукий", "Константинович");
        //Добовляем тренировки
        timetable.addNewTrainingSession(new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach, DayOfWeek.TUESDAY, new TimeOfDay(14, 00)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.THURSDAY, new TimeOfDay(16, 00)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(14, 00)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(13, 00)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 00)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.MONDAY, new TimeOfDay(11, 00)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.SATURDAY, new TimeOfDay(20, 00)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.FRIDAY, new TimeOfDay(19, 00)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.SUNDAY, new TimeOfDay(16, 00)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach4, DayOfWeek.FRIDAY, new TimeOfDay(17, 00)));
        //Вынес поля получения Списка что бы не дублировать
        gettedList = timetable.getCountByCoaches();
    }

    @Test
    public void shouldBeReturnZeroifCoachNotTraining() {
        CountOfTrainings coachWhitZeroTraining = null;

        for (CountOfTrainings coach : gettedList) {
            if (coach.getCoach().equals(coach3)) coachWhitZeroTraining = coach;
        }

        assertNull(coachWhitZeroTraining);
    }

    @Test
    public void sholdBeOneTrainingForCoach4() {
        CountOfTrainings infoCoach4 = null;
        for (CountOfTrainings trainer : gettedList) {
            if (trainer.getCoach().equals(coach4)) infoCoach4 = trainer;
        }

        assertEquals((Integer) 1, infoCoach4.getCount());
    }

    @Test
    public void givenSortedListFromLargeToSmall() {
        //Создаю тествоый список для сравнения. Данный список отсортирован вручную.
        List<CountOfTrainings> testListCorectSort = new ArrayList<>();
        testListCorectSort.add(new CountOfTrainings(coach2, 5));
        testListCorectSort.add(new CountOfTrainings(coach1, 3));
        testListCorectSort.add(new CountOfTrainings(coach, 2));
        testListCorectSort.add(new CountOfTrainings(coach4, 1));

        assertEquals(testListCorectSort, timetable.getCountByCoaches());

    }
}
