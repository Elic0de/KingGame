package si.f5.hatosaba.kinggame.runnables.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class SimpleTask<T> {
    private final List<Consumer<T>> consumers = new ArrayList<>();

    private SimpleTask() {
    }

    public Iterator<Consumer<T>> build() {
        return consumers.iterator();
    }

    public static <T> SimpleTask<T> builder() {
        return new SimpleTask<T>();
    }

    public SimpleTask<T> append(Consumer<T> consumer) {
        consumers.add(consumer);
        return this;
    }
}
