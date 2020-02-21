package br.com.patterncommons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

public class CollectionUtils {

    public static void main(String[] args) {
        CollectionUtils.join(Arrays.asList(1, 2, 3, 4, 5), Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10))
                .right(System.out::println)
                .left(System.out::println)
                .intersection(System.out::println)
                .rightIntersection(System.out::println)
                .leftIntersection(System.out::println)
                .outer(System.out::println);
    }

    public static <T> Joiner<T> join(Collection<T> c1, Collection<T> c2) {
        return new Joiner<>(c1, c2);
    }

    private static class Joiner<T> {

        private Collection<T> c1;
        private Collection<T> c2;
        private Collection<T> left;
        private Collection<T> right;
        private Collection<T> intersection;
        private Collection<T> outer;

        private Joiner(Collection<T> c1, Collection<T> c2) {
            this.c1 = c1;
            this.c2 = c2;
            this.left = new ArrayList<>();
            this.right = new ArrayList<>();
            this.intersection = new ArrayList<>();
            this.outer = new ArrayList<>();
            this.process();
        }

        private void process() {
            HashSet<T> h2 = new HashSet<>(c2);
            c1.forEach(i1 -> {
                if (h2.contains(i1)) {
                    intersection.add(i1);
                    h2.remove(i1);
                } else {
                    left.add(i1);
                    outer.add(i1);
                }
            });
            h2.forEach(i2 -> {
                right.add(i2);
                outer.add(i2);
            });
        }

        public Joiner<T> left(Consumer<Collection<T>> consumer) {
            consumer.accept(left);
            return this;
        }

        public Joiner<T> right(Consumer<Collection<T>> consumer) {
            consumer.accept(right);
            return this;
        }

        public Joiner<T> intersection(Consumer<Collection<T>> consumer) {
            consumer.accept(intersection);
            return this;
        }

        public Joiner<T> leftIntersection(Consumer<Collection<T>> consumer) {
            consumer.accept(c1);
            return this;
        }

        public Joiner<T> rightIntersection(Consumer<Collection<T>> consumer) {
            consumer.accept(c2);
            return this;
        }

        public Joiner<T> outer(Consumer<Collection<T>> consumer) {
            consumer.accept(outer);
            return this;
        }

    }

}
