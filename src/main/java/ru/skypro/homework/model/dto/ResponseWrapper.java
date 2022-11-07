package ru.skypro.homework.model.dto;

import lombok.Getter;

import java.util.Collection;
import java.util.Objects;

@Getter
public class ResponseWrapper<T> {

    private final int count;
    private final Collection<T> results;

    public ResponseWrapper(Collection<T> object) {
        this.count = object.size();
        this.results = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseWrapper<?> that = (ResponseWrapper<?>) o;
        return count == that.count && Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, results);
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "count=" + count +
                ", results=" + results +
                '}';
    }
}
