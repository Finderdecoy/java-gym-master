package ru.yandex.practicum.gym;

import java.util.Comparator;

public class CountOfTrainings implements Comparator<CountOfTrainings> {
    private Coach coach;
    private Integer count;

    public CountOfTrainings(Coach coach, Integer count) {
        this.coach = coach;
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public CountOfTrainings() {}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CountOfTrainings that = (CountOfTrainings) o;
        return coach.equals(that.coach) && count.equals(that.count);
    }

    @Override
    public int hashCode() {
        int result = coach.hashCode();
        result = 31 * result + count.hashCode();
        return result;
    }

    @Override
    public int compare(CountOfTrainings o1, CountOfTrainings o2) {
        return Integer.compare(o2.count, o1.count);
    }

    @Override
    public String toString() {
        return "CountOfTrainings{" +
                "coach=" + coach +
                ", count=" + count +
                '}';
    }
}
